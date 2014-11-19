package com.future.getfood;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.future.foodimg.DetectNetwork;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderDishes extends FragmentActivity {

	String dishname;
	String chkname;
	String price;
	FragmentManager fmanager;
	Fragment fragment;
	SupportMapFragment supportmapfragment;
	GoogleMap supportMap;

	TextView dish_name;
	TextView dish_price;
	TextView total_price;
	LatLng current_latlng;
	GPSTracker gps;
	double latitude, longitude;
	String add, city, country, sub1, sub2, state, zip;
	ImageView cls;
	EditText street, area, zipcode, landmark, phone_num;
	TextView edit, del;
	Button save;
	SessionManager sess;
	String user_id;
	protected HttpResponse response;
	protected String s;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.order);

		sess = new SessionManager(this);
		HashMap<String, String> map = sess.getUserDetails();
		user_id = map.get(SessionManager.KEY_ID);

		// getting value from MyAdapter
		Intent in = getIntent();
		dishname = in.getStringExtra("dish");
		chkname = in.getStringExtra("chk");
		price = in.getStringExtra("price");

		// getting current latlng
		gps = new GPSTracker(OrderDishes.this);
		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
			current_latlng = new LatLng(latitude, longitude);
		} else {

			gps.showSettingsAlert();
		}

		// set map position
		fmanager = getSupportFragmentManager();
		fragment = fmanager.findFragmentById(R.id.map);
		supportmapfragment = (SupportMapFragment) fragment;
		supportMap = supportmapfragment.getMap();

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target(current_latlng) // Sets the center of the map to
										// Golden Gate Bridge
				.zoom(17) // Sets the zoom
				.bearing(90) // Sets the orientation of the camera to east
				.tilt(30) // Sets the tilt of the camera to 30 degrees
				.build(); // Creates a CameraPosition from the builder
		supportMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));

		// ...........................address................................
		try {
			Geocoder geocoder;
			List<Address> addresses;
			geocoder = new Geocoder(this, Locale.getDefault());
			addresses = geocoder.getFromLocation(latitude, longitude, 1);

			add = addresses.get(0).getAddressLine(0);
			sub1 = addresses.get(0).getSubAdminArea();
			sub2 = addresses.get(0).getSubLocality();
			city = addresses.get(0).getLocality();
			state = addresses.get(0).getAdminArea();
			zip = addresses.get(0).getPostalCode();
			country = addresses.get(0).getCountryName();

		} catch (Exception e) {
			e.printStackTrace();
		}

		supportMap
				.addMarker(new MarkerOptions()
						.position(current_latlng)
						.title(sub2)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.location)));
		// view object creation
		dish_name = (TextView) findViewById(R.id.textView5);
		dish_price = (TextView) findViewById(R.id.textView6);
		total_price = (TextView) findViewById(R.id.textView8);
		edit = (TextView) findViewById(R.id.edit);
		del = (TextView) findViewById(R.id.delete);

		street = (EditText) findViewById(R.id.editText1);
		area = (EditText) findViewById(R.id.editText2);
		zipcode = (EditText) findViewById(R.id.editText3);
		landmark = (EditText) findViewById(R.id.editText4);
		phone_num = (EditText) findViewById(R.id.editText5);
		cls = (ImageView) findViewById(R.id.imageView4);

		save = (Button) findViewById(R.id.button1);

		// disable edittext box
		street.setFocusableInTouchMode(false);
		street.setFocusable(false);
		area.setFocusableInTouchMode(false);
		area.setFocusable(false);
		zipcode.setFocusableInTouchMode(false);
		zipcode.setFocusable(false);

		zipcode.setText(zip);
		street.setText(add + "," + sub2);
		area.setText(sub1 + "," + city);
		// set value
		dish_name.setText(dishname + "+" + chkname);
		dish_price.setText("Rs " + price);
		total_price.setText("Rs " + price);

		cls.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		edit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// enable edittext box
				street.setFocusableInTouchMode(true);
				street.setFocusable(true);
				area.setFocusableInTouchMode(true);
				area.setFocusable(true);
				zipcode.setFocusableInTouchMode(true);
				zipcode.setFocusable(true);
			}
		});

		del.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// enable edittext box
				street.setFocusableInTouchMode(true);
				street.setFocusable(true);
				area.setFocusableInTouchMode(true);
				area.setFocusable(true);
				zipcode.setFocusableInTouchMode(true);
				zipcode.setFocusable(true);

				street.setText("");
				area.setText("");
				zipcode.setText("");
			}
		});

		((ImageView) findViewById(R.id.imageView1))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});

		save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int ppp = phone_num.getText().toString().length();
				String adds = street.getText().toString() + ","
						+ area.getText().toString() + ","
						+ zipcode.getText().toString() + ","
						+ phone_num.getText().toString();

				if (street.getText().toString().equals("")) {

					street.setError("Please fill street name!");
					street.requestFocus();
				} else {

					if (area.getText().toString().equals("")) {

						area.setError("please fill area name!");
						area.requestFocus();
					} else {

						if (zipcode.getText().toString().equals("")) {

							zipcode.setError("Please fill pincode!");
							zipcode.requestFocus();
						} else {
							if (phone_num.getText().toString().equals("")) {

								phone_num
										.setError("Please fill correct phone number");
								phone_num.requestFocus();
							} else {
								if (ppp != 10) {

									phone_num
											.setError("Please fill correct phone number");
									phone_num.requestFocus();

								} else {
									userdetail();
									//dialog(adds);
								}
							}

						}
					}
				}

			}
		});
	}

	// method for sending user detail on server
	protected void userdetail() {
		// TODO Auto-generated method stub

		AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
			ProgressDialog dialog = new ProgressDialog(OrderDishes.this);

			@Override
			protected void onPreExecute() {
				// what to do before background task
				dialog.setMessage("Validating... ");
				dialog.setIndeterminate(true);
				dialog.show();
			}

			@Override
			protected Void doInBackground(Void... params) {

				// do your background operation here
				
				 JSONObject obj = new JSONObject();
		            try {
		                obj.put("firstname", "dharam");
		                obj.put("lastname", "Singh");
		                obj.put("address", street.getText().toString());
		                obj.put("area", area.getText().toString());
		                obj.put("landmark", landmark.getText().toString());
		                obj.put("phone", phone_num.getText().toString());
		                obj.put("zipcode", zipcode.getText().toString());
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

					 entity.addPart("data[Address][customer_id]", new StringBody(user_id));
					 entity.addPart("data[Address][data]", new StringBody(obj.toString()));
					 entity.addPart("data[Address][status]", new StringBody("1"));
					
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
			}

		};
		if ((DetectNetwork.hasConnection(getApplicationContext())))
			updateTask.execute((Void[]) null);

	}

	public void dialog(String addss) {

		final Dialog d = new Dialog(this);
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		d.setContentView(R.layout.confirmation_dialog);
		TextView dish_name = (TextView) d.findViewById(R.id.textView4);
		TextView dish_price = (TextView) d.findViewById(R.id.textView3);
		TextView dish_add = (TextView) d.findViewById(R.id.textView5);
		Button cls = (Button) d.findViewById(R.id.button1);
		Button cirm = (Button) d.findViewById(R.id.button2);
		d.show();

		dish_name.setText(dishname + "+" + chkname);
		dish_price.setText("Rs " + price);
		dish_add.setText(addss);

		cls.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				d.cancel();
			}
		});

		cirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				d.cancel();
				Intent in=new Intent(OrderDishes.this,PaymentProcess.class);
				startActivity(in);
				
			}
		});

	}
}
