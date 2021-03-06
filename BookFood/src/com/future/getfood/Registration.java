package com.future.getfood;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.future.foodimg.DetectNetwork;
import com.future.getfood.AnalyticsSampleApp.TrackerName;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.model.LatLng;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class Registration extends Activity {

	ImageView user_img;
	ImageView camera;
	TextView username;
	TextView emailid, creatprofile;
	EditText user_name;
	EditText user_email;
	Button cont;
	String dishname;
	String chkname;
	String price, chkk;
	String phonenum, cid;
	SessionManager sess;
	protected HttpResponse response;
	protected String s;
	private byte[] ba;
	private Typeface tf1, tf2, tf3, tf4;
	//String gcmid;
	String fname, lname, address, area, city, zipcode, mobile,chk_value;
	double latitude, longitude;
	private GPSTracker gps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.profile);

		tf1 = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");
		tf2 = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
		tf3 = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
		tf4 = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");

		sess = new SessionManager(this);

		HashMap<String, String> map = sess.getGCMID();
		//gcmid = map.get(SessionManager.KEY_GCMID);

		// set map position

		gps = new GPSTracker(Registration.this);
		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

		} else {

			gps.showSettingsAlert();
		}

		// getting value from UserLogin
		Intent in = getIntent();
		dishname = in.getStringExtra("dish");
		chkname = in.getStringExtra("chk");
		price = in.getStringExtra("price");
		phonenum = in.getStringExtra("phone");
		cid = in.getStringExtra("cid");
		fname = in.getStringExtra("fname");
		lname = in.getStringExtra("lname");
		address = in.getStringExtra("address");
		area = in.getStringExtra("area");

		city = in.getStringExtra("mcity");
		zipcode = in.getStringExtra("zipcode");
		mobile = in.getStringExtra("phone_num");
		chk_value = in.getStringExtra("chk_value");
		
		// view object creation

		user_img = (ImageView) findViewById(R.id.imageView1);
		camera = (ImageView) findViewById(R.id.imageView2);

		creatprofile = (TextView) findViewById(R.id.textView1);
		username = (TextView) findViewById(R.id.textView2);
		emailid = (TextView) findViewById(R.id.textView4);
		creatprofile.setTypeface(tf1);
		username.setTypeface(tf1);
		emailid.setTypeface(tf1);

		user_name = (EditText) findViewById(R.id.editText1);
		user_email = (EditText) findViewById(R.id.editText2);

		cont = (Button) findViewById(R.id.button1);

		// google analytics
		Tracker t = ((AnalyticsSampleApp) Registration.this.getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName("Registration");
		t.send(new HitBuilders.AppViewBuilder().build());

		camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select();
			}
		});

		((RelativeLayout)findViewById(R.id.nnnn)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		cont.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String uname = user_name.getText().toString();

				if (uname.equals("")) {

					user_name.setError("Please fill username!");
				} else {

					if (user_email.getText().toString().equals("")) {

						user_email.setError("Please fill emailid!");
					} else {
						if (!validationEmail(user_email.getText().toString())) {

							user_email.setError("Please fill correct emailid!");

						} else {
							Tracker t = ((AnalyticsSampleApp) Registration.this
									.getApplication())
									.getTracker(TrackerName.APP_TRACKER);
							// Build and send an Event.
							t.send(new HitBuilders.EventBuilder()
									.setCategory("Registration") // category
																	// i.e.
																	// Player
																	// Buttons
									.setAction("User signup") // action i.e.
																// Play
									.setLabel("clicked") // label i.e. any
															// meta-data
									.build());

							senddetail();
						}
					}
				}
			}
		});
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

	// method for sending user detail on server
	protected void senddetail() {
		// TODO Auto-generated method stub

		AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
			ProgressDialog dialog = new ProgressDialog(Registration.this);

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
							+ "api/customers/add.json?a=" + milli;

					MultipartEntity entity = new MultipartEntity(
							HttpMultipartMode.BROWSER_COMPATIBLE);

					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(url);

					long currentDateandTime = System.currentTimeMillis();
					try {
						if (ba != null)
							entity.addPart("data[Customer][image]",
									new ByteArrayBody(ba, currentDateandTime
											+ ".png"));
					} catch (Exception e) {
						e.printStackTrace();
					}

					entity.addPart("data[Customer][name]", new StringBody(
							user_name.getText().toString()));

					entity.addPart("data[Customer][email]", new StringBody(
							user_email.getText().toString()));
					entity.addPart("data[Customer][mobile_number]",
							new StringBody(phonenum));
					entity.addPart("data[Customer][deviceId]", new StringBody(
							getDeviceDetail()));
					// device token static
					entity.addPart("data[Customer][device_token]",
							new StringBody("kkkdi7999900hhhhhhhh"));

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

				try {
					JSONObject obj = new JSONObject(s);
					JSONObject obj1 = obj.getJSONObject("data");
					JSONObject obj2 = obj1.getJSONObject("record");
					JSONObject obj3 = obj2.getJSONObject("Customer");
					String custmor_Id = obj3.getString("id");
					String custmor_name = obj3.getString("name");
					String custmor_email = obj3.getString("email");
					String custmor_img = obj3.getString("image");
					String custmor_mob = obj3.getString("mobile_number");
					sess.setId(custmor_Id, custmor_name, custmor_email,
							custmor_img, custmor_mob);
//					Intent in = new Intent(Registration.this, OrderDishes.class);
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

				dialog.cancel();
			}

		};
		if ((DetectNetwork.hasConnection(getApplicationContext())))
			updateTask.execute((Void[]) null);

	}

	@SuppressWarnings("deprecation")
	public void alert(String msg) {

		final AlertDialog alertDialog = new AlertDialog.Builder(
				Registration.this).create();

		// Setting Dialog Title
		alertDialog.setTitle("FoodApp");

		// Setting Dialog Message
		alertDialog.setMessage(msg);

		// Setting Icon to Dialog
		alertDialog.setIcon(R.drawable.ic_launcher);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				alertDialog.cancel();
				finish();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

	String getDeviceDetail() {

		return Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);

	}

	protected void select() {
		// TODO Auto-generated method stub
		View choose = null;

		LayoutInflater li = LayoutInflater.from(getApplicationContext());

		choose = li.inflate(R.layout.choosedialog, null);

		Button gallery = (Button) choose.findViewById(R.id.gallery);
		Button camera = (Button) choose.findViewById(R.id.Camera);
		Button cancel = (Button) choose.findViewById(R.id.Cancel);
		final Dialog mDialog = new Dialog(Registration.this,
				android.R.style.Theme_Translucent_NoTitleBar);

		mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		mDialog.setContentView(choose);

		mDialog.show();
		gallery.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, 0);

				mDialog.dismiss();
			}
		});

		camera.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, 1);

				mDialog.dismiss();
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				mDialog.dismiss();
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent returnimage) {
		super.onActivityResult(requestCode, resultCode, returnimage);

		switch (requestCode) {
		case 0:
			if (resultCode == RESULT_OK) {

				Uri selectedImage = returnimage.getData();

				// coverpath.setText(selectedImage.getPath());

				try {
					FileInputStream imageStream = (FileInputStream) getContentResolver()
							.openInputStream(selectedImage);

					Bitmap image1 = BitmapFactory.decodeStream(imageStream);
					Bitmap scaled = Bitmap.createScaledBitmap(image1, 60, 60,
							true);
					ByteArrayOutputStream bao = new ByteArrayOutputStream();
					// user_img.setScaleType(ScaleType.CENTER_CROP);

					image1.compress(Bitmap.CompressFormat.JPEG, 90, bao);
					ba = bao.toByteArray();
					user_img.setImageBitmap(Bitmap.createScaledBitmap(scaled,
							100, 100, true));
					// Imgveprofilepic.setImageBitmap(decodeFile(imageStream));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			break;
		case 1:
			if (resultCode == RESULT_OK) {
				Bitmap image1 = (Bitmap) returnimage.getExtras().get("data");
				// coverpath.setText("By Cam");
				Bitmap scaled = Bitmap.createScaledBitmap(image1, 60, 60, true);
				ByteArrayOutputStream bao = new ByteArrayOutputStream();

				image1.compress(Bitmap.CompressFormat.JPEG, 90, bao);
				ba = bao.toByteArray();
				user_img.setImageBitmap(Bitmap.createScaledBitmap(scaled, 100,
						100, true));
			}
			break;

		}
	}

	private boolean validationEmail(String str) {

		if (str.indexOf("@") > 0 && str.indexOf(".") > 0) {

			return true;

		} else {
			return false;
		}

	}

	// order process

	// method for sending user detail on server
	protected void userdetail(final String user_id) {
		// TODO Auto-generated method stub

		AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
			ProgressDialog dialog = new ProgressDialog(Registration.this);

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

					Intent in = new Intent(Registration.this,
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
			ProgressDialog dialog = new ProgressDialog(Registration.this);

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

					Intent in = new Intent(Registration.this,
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
