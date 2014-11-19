package com.future.getfood;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.SessionStore;
import com.future.foodimg.DetectNetwork;

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
	String st;
	private static final String[] PERMISSIONS = new String[] {
			"publish_stream", "read_stream", "offline_access" };
	String me;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       
       sess=new SessionManager(this);
		
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
					//name = jsonObj.getString("name");
					id = jsonObj.getString("id");
//					try {
//						email = jsonObj.getString("email");
//					} catch (Exception e) {
//						email = id + "@facebook.com";
//					}

					//Log.i("fb", name + " " + email + " " + id);
					// what = 0;
					// post();

				} catch (Exception ex) {
					ex.printStackTrace();
				}

				try {

					long st=System.currentTimeMillis();
					String url = getResources().getString(R.string.url)
							+ "api/customers/add.json?a="+st;

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

				try{
					JSONObject obj=new JSONObject(s);
					String custmor_Id=obj.getString("data");
					sess.setId(custmor_Id);
				}catch(Exception e){
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
}