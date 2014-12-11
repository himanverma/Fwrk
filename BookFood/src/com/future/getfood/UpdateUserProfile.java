package com.future.getfood;

import java.io.ByteArrayOutputStream;
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
import org.json.JSONObject;

import com.future.foodimg.DetectNetwork;
import com.future.foodimg.ImageLoader;
import com.future.getfood.AnalyticsSampleApp.TrackerName;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

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
import android.text.Editable;
import android.text.Selection;
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

public class UpdateUserProfile extends Activity {

	ImageView user_img;
	ImageView camera;
	TextView username;
	TextView emailid, creatprofile;
	EditText user_name;
	EditText user_email;
	EditText user_mob;
	Button cont;
	String name;
	String photo;
	String email;
	String phonenum;
	String userid;
	SessionManager sess;
	protected HttpResponse response;
	protected String s;
	private byte[] ba;
	private Typeface tf1, tf2, tf3, tf4;
	String gcmid;
	ImageLoader il;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_profile);

		il = new ImageLoader(this);
		tf1 = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");
		tf2 = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
		tf3 = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
		tf4 = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");

		sess = new SessionManager(this);

		HashMap<String, String> map = sess.getGCMID();
		gcmid = map.get(SessionManager.KEY_GCMID);
		HashMap<String, String> map1 = sess.getUserDetails();
		userid = map1.get(SessionManager.KEY_ID);
		phonenum = map1.get(SessionManager.KEY_PHONE);
		name = map1.get(SessionManager.KEY_USER);
		photo = map1.get(SessionManager.KEY_PHOTO);
		email = map1.get(SessionManager.KEY_EMAIL);
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
		user_mob = (EditText) findViewById(R.id.editText3);

		user_name.setText(name);
		Editable etext = user_name.getText();
		Selection.setSelection(etext, etext.length());
		user_email.setText(email);

		if (phonenum.equals("null")) {

			user_mob.setText("");

		} else {

			user_mob.setText(phonenum);

		}
		il.DisplayImage(photo, user_img);

		cont = (Button) findViewById(R.id.button1);

		// google analytics
		Tracker t = ((AnalyticsSampleApp) UpdateUserProfile.this
				.getApplication()).getTracker(TrackerName.APP_TRACKER);
		t.setScreenName("UpdateUserProfile");
		t.send(new HitBuilders.AppViewBuilder().build());

		((RelativeLayout) findViewById(R.id.img))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

//						Intent in = new Intent(UpdateUserProfile.this,
//								UserProfile.class);
//						startActivity(in);
						finish();
					}
				});

		camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				select();
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

							if (user_mob.getText().toString().length() > 9) {

								Tracker t = ((AnalyticsSampleApp) UpdateUserProfile.this
										.getApplication())
										.getTracker(TrackerName.APP_TRACKER);
								// Build and send an Event.
								t.send(new HitBuilders.EventBuilder()
										.setCategory("Update Own Profile") // category
																			// i.e.
																			// Player
																			// Buttons
										.setAction("Update Profile") // action
																		// i.e.
																		// Play
										.setLabel("clicked") // label i.e. any
																// meta-data
										.build());

								senddetail();
							} else {

								user_mob.setError("Please fill correct Phone number!");
							}

						}
					}
				}
			}
		});
	}

	// @Override
	// protected void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	//
	// sess = new SessionManager(this);
	//
	// HashMap<String, String> map = sess.getGCMID();
	// gcmid = map.get(SessionManager.KEY_GCMID);
	// HashMap<String, String> map1 = sess.getUserDetails();
	// userid = map1.get(SessionManager.KEY_ID);
	// phonenum = map1.get(SessionManager.KEY_PHONE);
	// name = map1.get(SessionManager.KEY_USER);
	// photo = map1.get(SessionManager.KEY_PHOTO);
	// email = map1.get(SessionManager.KEY_EMAIL);
	//
	// }
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent in = new Intent(UpdateUserProfile.this, UserProfile.class);
		startActivity(in);
		finish();
	}

	// method for sending user detail on server
	protected void senddetail() {
		// TODO Auto-generated method stub

		AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
			ProgressDialog dialog = new ProgressDialog(UpdateUserProfile.this);

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
							+ "api/customers/edit/" + userid + ".json?a="
							+ milli;

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
							new StringBody(user_mob.getText().toString()));
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
					JSONObject obj2 = obj1.getJSONObject("list");
					JSONObject obj3 = obj2.getJSONObject("Customer");

					String custmor_Id = obj3.getString("id");
					String custmor_name = obj3.getString("name");
					String custmor_email = obj3.getString("email");
					String custmor_img = obj3.getString("image");
					String custmor_mob = obj3.getString("mobile_number");

					sess.setId(custmor_Id, custmor_name, custmor_email,
							custmor_img, custmor_mob);

					alert("Profile updated successfully.");

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
				UpdateUserProfile.this).create();

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
				Intent in = new Intent(UpdateUserProfile.this,
						UserProfile.class);
				startActivity(in);
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
		final Dialog mDialog = new Dialog(UpdateUserProfile.this,
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
}
