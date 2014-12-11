package com.future.getfood;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.SessionStore;
import com.future.foodimg.DetectNetwork;
import com.future.getfood.AnalyticsSampleApp.TrackerName;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class FacebookLogin extends Activity {
	private Facebook mFacebook;
	private ProgressDialog mProgress;
	protected String s;
	String name = "";
	String email = "";
	String id = "";
	GPSTracker gps;
	SessionManager sess;
	double latitude, longitude;
	String dishname;
	String chkname;
	String price;
	String cid, chkk;
	String fname, lname, address, area, city, zipcode, mobile,chk_value;
	String st;
	private static final String[] PERMISSIONS = new String[] {
			"publish_stream", "read_stream", "offline_access" };
	String me;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// getting value from UserLogin
		Intent in = getIntent();
		dishname = in.getStringExtra("dish");
		chkname = in.getStringExtra("chk");
		price = in.getStringExtra("price");
		cid = in.getStringExtra("cid");
		
		fname = in.getStringExtra("fname");
		lname = in.getStringExtra("lname");
		address = in.getStringExtra("address");
		area = in.getStringExtra("area");

		city = in.getStringExtra("mcity");
		zipcode = in.getStringExtra("zipcode");
		mobile = in.getStringExtra("phone_num");
		chk_value = in.getStringExtra("chk_value");
		

		Log.e("nnnn", dishname + "  " + chkname);
		sess = new SessionManager(this);

		Tracker t = ((AnalyticsSampleApp) FacebookLogin.this.getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName("FacebookLogin make facebook login");
		t.send(new HitBuilders.AppViewBuilder().build());

		// .....................GPS......................................................
		gps = new GPSTracker(FacebookLogin.this);

		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

		} else {

			gps.showSettingsAlert();
		}

		// ...............................................................................

		mProgress = new ProgressDialog(this);
		mFacebook = new Facebook(getResources().getString(R.string.fbid));

		SessionStore.restore(mFacebook, this);

		if (mFacebook.isSessionValid()) {

			String name = SessionStore.getName(this);
			name = (name.equals("")) ? "Unknown" : name;

		}

		onFacebookClick();

	}

	@Override
	public void onStart() {
		super.onStart();
		GoogleAnalytics.getInstance(this).reportActivityStart(this);

	}

	@Override
	public void onStop() {
		super.onStop();
		GoogleAnalytics.getInstance(this).reportActivityStop(this);

	}

	private void onFacebookClick() {
		if (mFacebook.isSessionValid()) {
			post();

		} else {

			mFacebook.authorize(this, PERMISSIONS, -1,
					new FbLoginDialogListener());
		}
	}

	private final class FbLoginDialogListener implements DialogListener {
		public void onComplete(Bundle values) {
			SessionStore.save(mFacebook, FacebookLogin.this);

			post();
		}

		public void onFacebookError(FacebookError error) {
			Toast.makeText(FacebookLogin.this, "Facebook connection failed",
					Toast.LENGTH_SHORT).show();

		}

		public void onError(DialogError error) {
			Toast.makeText(FacebookLogin.this, "Facebook connection failed",
					Toast.LENGTH_SHORT).show();

		}

		public void onCancel() {

		}
	}

	private void post() {
		// TODO Auto-generated method stub
		AsyncTask<Void, Void, Void> updateTask1 = new AsyncTask<Void, Void, Void>() {
			ProgressDialog dialog = new ProgressDialog(FacebookLogin.this);
			String url = null;

			@Override
			protected void onPreExecute() {
				// what to do before background task
				dialog.setTitle("Registering You on FoodApp");
				dialog.setCancelable(false);
				dialog.setIndeterminate(true);
				dialog.show();
			}

			@Override
			protected Void doInBackground(Void... params) {
				try {
					me = mFacebook.request("me");

					JSONObject jsonObj = (JSONObject) new JSONTokener(me)
							.nextValue();
					name = jsonObj.getString("name");
					id = jsonObj.getString("id");
					try {
						email = jsonObj.getString("email");
					} catch (Exception e) {
						email = id + "@facebook.com";
					}

					// Log.i("fb", name + " " + email + " " + id);
					// what = 0;
					// post();

				} catch (Exception ex) {
					ex.printStackTrace();
				}

				try {

					long st = System.currentTimeMillis();
					String url = getResources().getString(R.string.url)
							+ "api/customers/add.json?a=" + st;

					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(url);

					// Add your data
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
							8);
					nameValuePairs.add(new BasicNameValuePair(
							"data[Customer][fbid]", id));

					nameValuePairs.add(new BasicNameValuePair(
							"data[Customer][deviceId]", getDeviceDetail()));

					nameValuePairs.add(new BasicNameValuePair(
							"data[Customer][name]", name));

					nameValuePairs.add(new BasicNameValuePair(
							"data[Customer][email]", email));

					nameValuePairs.add(new BasicNameValuePair(
							"data[Customer][fbrawdata]", me));

					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					// Execute HTTP Post Request
					HttpResponse response = httpclient.execute(httppost);

					s = EntityUtils.toString(response.getEntity());
					Log.e("fhgfhj", s);
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// what to do when background task is completed

				dialog.dismiss();

				try {
					JSONObject obj = new JSONObject(s);
					JSONObject obj1 = obj.getJSONObject("data");
					JSONObject obj2 = obj1.getJSONObject("record");
					JSONObject obj3 = obj2.getJSONObject("Customer");
					String custmor_Id = obj3.getString("id");
					String custmor_name = obj3.getString("name");
					String custmor_email = obj3.getString("email");
					String custmor_fbid = obj3.getString("fbid");
					String custmor_mob = obj3.getString("mobile_number");
					String img_url = "https://graph.facebook.com/"
							+ custmor_fbid + "/picture?type=small";
					sess.setId(custmor_Id, custmor_name, custmor_email,
							img_url, custmor_mob);

//					Intent in = new Intent(FacebookLogin.this,
//							OrderDishes.class);
//					in.putExtra("dish", dishname);
//					in.putExtra("chk", chkname);
//					in.putExtra("price", price);
//					in.putExtra("cid", cid);
//					in.putExtra("chkk", chkk);
//					startActivity(in);
//					finish();
					
					userdetail(custmor_Id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		if ((DetectNetwork.hasConnection(getApplicationContext())))
			updateTask1.execute((Void[]) null);

	}

	String getDeviceDetail() {

		return Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);

	}

	private void fbLogout() {
		mProgress.setMessage("Disconnecting from Facebook");
		mProgress.show();

		new Thread() {
			@Override
			public void run() {
				SessionStore.clear(FacebookLogin.this);

				int what = 1;

				try {
					mFacebook.logout(FacebookLogin.this);

					what = 0;
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				mHandler.sendMessage(mHandler.obtainMessage(what));
			}
		}.start();
	}

	private Handler mFbHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();

			if (msg.what == 0) {
				String username = (String) msg.obj;
				username = (username.equals("")) ? "No Name" : username;

				SessionStore.saveName(username, FacebookLogin.this);

				Toast.makeText(FacebookLogin.this,
						"Connected to Facebook as " + username,
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(FacebookLogin.this, "Connected to Facebook",
						Toast.LENGTH_SHORT).show();
			}
		}
	};

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();

			if (msg.what == 1) {
				Toast.makeText(FacebookLogin.this, "Facebook logout failed",
						Toast.LENGTH_SHORT).show();
			} else {

				Toast.makeText(FacebookLogin.this,
						"Disconnected from Facebook", Toast.LENGTH_SHORT)
						.show();
			}
		}
	};
	protected HttpResponse response;
	
	
	
	// order process

		// method for sending user detail on server
		protected void userdetail(final String user_id) {
			// TODO Auto-generated method stub

			AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
				ProgressDialog dialog = new ProgressDialog(FacebookLogin.this);

				@Override
				protected void onPreExecute() {
					// what to do before background task
					dialog.setMessage("Validating... ");
					dialog.setIndeterminate(true);
					dialog.setCancelable(false);
					dialog.show();
				}

				@Override
				protected Void doInBackground(Void... params) {

					// do your background operation here

					JSONObject obj = new JSONObject();
					try {
						obj.put("firstname", fname);
						obj.put("lastname", lname);
						obj.put("address", address);
						obj.put("area", area);
						obj.put("city", city);
						obj.put("phone", mobile);
						obj.put("zipcode", zipcode);
						obj.put("lat", String.valueOf(latitude));
						obj.put("lng", String.valueOf(longitude));

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						long milli = System.currentTimeMillis();
						String url = getResources().getString(R.string.url)
								+ "api/Addresses/add.json?a=" + milli;

						MultipartEntity entity = new MultipartEntity(
								HttpMultipartMode.BROWSER_COMPATIBLE);

						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(url);

						entity.addPart("data[Address][customer_id]",
								new StringBody(user_id));
						entity.addPart("data[Address][data]",
								new StringBody(obj.toString()));
						entity.addPart("data[Address][status]", new StringBody("1"));

						entity.addPart("data[Address][f_name]", new StringBody(
								fname));
						entity.addPart("data[Address][l_name]", new StringBody(
								lname));
						entity.addPart("data[Address][address]", new StringBody(
								address));

						entity.addPart("data[Address][area]", new StringBody(area
								));
						entity.addPart("data[Address][city]", new StringBody(city
								));
						entity.addPart("data[Address][zipcode]", new StringBody(
								zipcode));

						entity.addPart("data[Address][phone_number]",
								new StringBody(mobile));
						entity.addPart("data[Address][lat]",
								new StringBody(String.valueOf(latitude)));
						entity.addPart("data[Address][long]",
								new StringBody(String.valueOf(longitude)));

						httppost.setEntity(entity);

						response = httpclient.execute(httppost);

						s = EntityUtils.toString(response.getEntity());
						// Log.e("fhgfhj", s);

					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					// what to do when background task is completed

					dialog.cancel();
					try {
						JSONObject obj = new JSONObject(s);
						JSONObject obj1 = obj.getJSONObject("data");
						String msg = obj1.getString("msg");
						String address_id = obj1.getString("addressid");
						if (msg.equals("success")) {
							String add = address + ","
									+ area + ","
									+ city + ","
									+ zipcode;
							dialogd(user_id,add, address_id);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			};
			if ((DetectNetwork.hasConnection(getApplicationContext())))
				updateTask.execute((Void[]) null);

		}

		public void dialogd(final String user_id,String addss, final String addid) {

			final Dialog d = new Dialog(this);
			d.requestWindowFeature(Window.FEATURE_NO_TITLE);
			d.setContentView(R.layout.confirmation_dialog);
			d.setCanceledOnTouchOutside(false);
			TextView dish_name = (TextView) d.findViewById(R.id.textView4);
			TextView dish_price = (TextView) d.findViewById(R.id.textView3);
			TextView dish_user = (TextView) d.findViewById(R.id.textView5);
			TextView dish_mob = (TextView) d.findViewById(R.id.textView9);
			TextView dish_add = (TextView) d.findViewById(R.id.textView10);
			TextView payment_type = (TextView) d.findViewById(R.id.textView8);
			Button cls = (Button) d.findViewById(R.id.button1);
			Button confirm = (Button) d.findViewById(R.id.button2);
			d.show();

			dish_name.setText(dishname + "+" + chkname);
			dish_price.setText("Rs " + price);

			dish_user.setText(fname + " "
					+ lname);
			dish_mob.setText(mobile);

			dish_add.setText(addss);
			payment_type.setText(chk_value);

			cls.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					d.cancel();
				}
			});

			confirm.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					d.cancel();
					if (chk_value.equals("Cash on Delivery")) {

						Intent in = new Intent(FacebookLogin.this,
								PaymentProcess.class);
						in.putExtra("url", "cod");
						startActivity(in);

					} else {

						orderProcess(user_id,addid);
					}

				}
			});

		}

		// method for sending user detail on server
		protected void orderProcess(final String user_id,final String addid) {
			// TODO Auto-generated method stub

			AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
				ProgressDialog dialog = new ProgressDialog(FacebookLogin.this);

				@Override
				protected void onPreExecute() {
					// what to do before background task
					dialog.setMessage("Validating... ");
					dialog.setIndeterminate(true);
					dialog.setCancelable(false);
					dialog.show();
				}

				@Override
				protected Void doInBackground(Void... params) {

					// do your background operation here

					try {
						long milli = System.currentTimeMillis();
						String url = getResources().getString(R.string.url)
								+ "api/orders/add.json?a=" + milli;

						MultipartEntity entity = new MultipartEntity(
								HttpMultipartMode.BROWSER_COMPATIBLE);

						HttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost(url);

						entity.addPart("data[Order][customer_id]", new StringBody(
								user_id));

						entity.addPart("data[Order][price]", new StringBody(price));

						entity.addPart("data[Order][combination_id]",
								new StringBody(cid));
						// mmmmmmmm
						entity.addPart("data[Order][address_id]", new StringBody(
								addid));

						entity.addPart(" data[Order][recipe_names]",
								new StringBody(dishname + "+" + chkname));

						entity.addPart("data[Order][status]", new StringBody("1"));
						entity.addPart("data[Order][paid_via]", new StringBody(
								chk_value));

						entity.addPart("data[Order][lat]",
								new StringBody(String.valueOf(latitude)));
						entity.addPart("data[Order][long]",
								new StringBody(String.valueOf(longitude)));

						httppost.setEntity(entity);

						response = httpclient.execute(httppost);

						s = EntityUtils.toString(response.getEntity());
						Log.e("fhgfhj", s);

					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					// what to do when background task is completed

					dialog.cancel();
					try {
						JSONObject obj = new JSONObject(s);
						JSONObject obj1 = obj.getJSONObject("data");
						String st = obj1.getString("msg");
						String url = obj1.getString("url");

						Intent in = new Intent(FacebookLogin.this,
								PaymentProcess.class);
						in.putExtra("url", url);
						startActivity(in);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			};
			if ((DetectNetwork.hasConnection(getApplicationContext())))
				updateTask.execute((Void[]) null);

		}
}