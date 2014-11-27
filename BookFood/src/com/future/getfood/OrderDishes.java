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
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDishes extends FragmentActivity {

	String dishname;
	String chkname;
	String price;
	String cid;
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
	Button cls;
	EditText fname, lname, address, area, zipcode, mcity, phone_num;
	CheckBox cod, debit, credit, netb;
	String chk_value = "na";
	Button save;
	SessionManager sess;
	String user_id;
	protected HttpResponse response;
	protected String s;
	private Typeface tf1, tf2, tf3, tf4;
	RelativeLayout rel6;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.order);

		tf1 = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");
		tf2 = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
		tf3 = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
		tf4 = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");

		sess = new SessionManager(this);
		HashMap<String, String> map = sess.getUserDetails();
		user_id = map.get(SessionManager.KEY_ID);

		// getting value from MyAdapter
		Intent in = getIntent();
		dishname = in.getStringExtra("dish");
		chkname = in.getStringExtra("chk");
		price = in.getStringExtra("price");
		cid = in.getStringExtra("cid");

		// view object creation
		dish_name = (TextView) findViewById(R.id.textView5);
		dish_price = (TextView) findViewById(R.id.textView6);
		total_price = (TextView) findViewById(R.id.textView8);

		fname = (EditText) findViewById(R.id.editText1);
		lname = (EditText) findViewById(R.id.editText2);
		address = (EditText) findViewById(R.id.editText3);
		area = (EditText) findViewById(R.id.editText4);
		mcity = (EditText) findViewById(R.id.editText5);
		zipcode = (EditText) findViewById(R.id.editText6);
		phone_num = (EditText) findViewById(R.id.editText7);

		cls = (Button) findViewById(R.id.imageView4);

		save = (Button) findViewById(R.id.button1);

		// zipcode.setText(zip);
		// address.setText(add + "," + sub2);
		// area.setText(sub1);
		// mcity.setText(city);
		//
		// // set value
		// dish_name.setText(dishname + "+" + chkname);
		// dish_name.setTypeface(tf2);
		// dish_price.setText("Rs " + price);
		// dish_price.setTypeface(tf2);
		// total_price.setText("Rs " + price);
		// total_price.setTypeface(tf2);
		((TextView) findViewById(R.id.textView5)).setTypeface(tf1);
		((TextView) findViewById(R.id.textView7)).setTypeface(tf1);
		((TextView) findViewById(R.id.textView2)).setTypeface(tf1);
		((TextView) findViewById(R.id.textView1)).setTypeface(tf1);
		((TextView) findViewById(R.id.textView9)).setTypeface(tf1);
		((TextView) findViewById(R.id.textView11)).setTypeface(tf1);
		((TextView) findViewById(R.id.textView13)).setTypeface(tf1);
		((TextView) findViewById(R.id.textView15)).setTypeface(tf1);
		((TextView) findViewById(R.id.textView17)).setTypeface(tf1);
		((TextView) findViewById(R.id.textView18)).setTypeface(tf1);
		((TextView) findViewById(R.id.textView20)).setTypeface(tf1);
		((TextView) findViewById(R.id.textView26)).setTypeface(tf1);
		((TextView) findViewById(R.id.textView21)).setTypeface(tf1);
		((TextView) findViewById(R.id.textView22)).setTypeface(tf1);
		((TextView) findViewById(R.id.textView3)).setTypeface(tf1);
		((TextView) findViewById(R.id.textView4)).setTypeface(tf1);

		cod = (CheckBox) findViewById(R.id.checkBox1);
		debit = (CheckBox) findViewById(R.id.checkBox2);
		credit = (CheckBox) findViewById(R.id.checkBox3);
		netb = (CheckBox) findViewById(R.id.checkBox4);
		cod.setTypeface(tf2);
		debit.setTypeface(tf2);
		credit.setTypeface(tf2);
		netb.setTypeface(tf2);

		rel6 = (RelativeLayout) findViewById(R.id.rel6);
		// getting current latlng

		((RelativeLayout) findViewById(R.id.nnn2))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (user_id.equals("0")) {
							Toast.makeText(getApplicationContext(),
									"Make your profile first.", 5000).show();
						} else {

							Intent in = new Intent(OrderDishes.this,
									UserProfile.class);
							startActivity(in);
						}
					}
				});

		// set map position

		gps = new GPSTracker(OrderDishes.this);
		// getdetail();

		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
			current_latlng = new LatLng(latitude, longitude);

		} else {

			gps.showSettingsAlert();
		}
		fmanager = getSupportFragmentManager();
		fragment = fmanager.findFragmentById(R.id.map);
		supportmapfragment = (SupportMapFragment) fragment;
		supportMap = supportmapfragment.getMap();

		GetCurrentAddress currentadd = new GetCurrentAddress();
		currentadd.execute();

		// CameraPosition cameraPosition = new CameraPosition.Builder()
		// .target(current_latlng) // Sets the center of the map to
		// // Golden Gate Bridge
		// .zoom(17) // Sets the zoom
		// .bearing(90) // Sets the orientation of the camera to east
		// .tilt(30) // Sets the tilt of the camera to 30 degrees
		// .build(); // Creates a CameraPosition from the builder
		// supportMap.animateCamera(CameraUpdateFactory
		// .newCameraPosition(cameraPosition));

		// // ...........................address................................
		// try {
		// Geocoder geocoder;
		// List<Address> addresses;
		// geocoder = new Geocoder(this, Locale.getDefault());
		// addresses = geocoder.getFromLocation(latitude, longitude, 1);
		//
		// add = addresses.get(0).getAddressLine(0);
		// sub1 = addresses.get(0).getSubAdminArea();
		// sub2 = addresses.get(0).getSubLocality();
		// city = addresses.get(0).getLocality();
		// state = addresses.get(0).getAdminArea();
		// zip = addresses.get(0).getPostalCode();
		// country = addresses.get(0).getCountryName();
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//
		// supportMap
		// .addMarker(new MarkerOptions()
		// .position(current_latlng)
		// .title(sub2)
		// .icon(BitmapDescriptorFactory
		// .fromResource(R.drawable.location)));

		cod.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub

				if (cod.isChecked()) {

					debit.setChecked(false);
					credit.setChecked(false);
					netb.setChecked(false);
					chk_value = "Cash on Delivery";
				}
			}
		});

		debit.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub

				if (debit.isChecked()) {

					cod.setChecked(false);
					credit.setChecked(false);
					netb.setChecked(false);
					chk_value = "Debit Card";
				}
			}
		});

		credit.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub

				if (credit.isChecked()) {

					debit.setChecked(false);
					cod.setChecked(false);
					netb.setChecked(false);
					chk_value = "Credit Card";
				}
			}
		});

		netb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub

				if (netb.isChecked()) {

					debit.setChecked(false);
					credit.setChecked(false);
					cod.setChecked(false);
					chk_value = "Net Banking";
				}
			}
		});
		cls.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		((RelativeLayout) findViewById(R.id.nnn1))
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
				String adds = address.getText().toString() + ","
						+ area.getText().toString() + ","
						+ zipcode.getText().toString() + ","
						+ phone_num.getText().toString();
				if (fname.getText().toString().equals("")) {

					fname.setError("Please fill street name!");
					fname.requestFocus();
				} else {
					if (lname.getText().toString().equals("")) {

						lname.setError("Please fill street name!");
						lname.requestFocus();
					} else {

						if (address.getText().toString().equals("")) {

							address.setError("Please fill street name!");
							address.requestFocus();
						} else {

							if (area.getText().toString().equals("")) {

								area.setError("please fill area name!");
								area.requestFocus();
							} else {

								if (mcity.getText().toString().equals("")) {

									mcity.setError("Please fill pincode!");
									mcity.requestFocus();

								} else {

									if (zipcode.getText().toString().equals("")) {

										zipcode.setError("Please fill pincode!");
										zipcode.requestFocus();
									} else {
										if (phone_num.getText().toString()
												.equals("")) {

											phone_num
													.setError("Please fill correct phone number");
											phone_num.requestFocus();
										} else {
											if (ppp != 10) {

												phone_num
														.setError("Please fill correct phone number");
												phone_num.requestFocus();

											} else {
												if (chk_value.equals("na")) {
													Toast.makeText(
															OrderDishes.this,
															"Please select payment type",
															5000).show();
												} else {
													userdetail();
												}
											}
										}

									}
								}
							}
						}

					}

				}

			}
		});
	}

	private class GetCurrentAddress extends AsyncTask<String, String, Void> {
		ProgressDialog dialog = new ProgressDialog(OrderDishes.this);

		@Override
		protected void onPreExecute() {
			// what to do before background task
			dialog.setMessage("Validating... ");
			dialog.setIndeterminate(true);
			dialog.show();
		}

		@Override
		protected Void doInBackground(String... urls) {

			try {
				Geocoder geocoder = new Geocoder(OrderDishes.this,
						Locale.getDefault());
				List<Address> addresses = geocoder.getFromLocation(latitude,
						longitude, 1);
				if (addresses.size() > 0) {
					Address address1 = addresses.get(0);

					add = address1.getAddressLine(0);
					sub1 = address1.getSubAdminArea();
					sub2 = address1.getSubLocality();
					city = address1.getLocality();
					state = address1.getAdminArea();
					zip = address1.getPostalCode();
					country = address1.getCountryName();

				}
			} catch (IOException e) {
				Log.e("tag", e.getMessage());
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void resultString) {
			dialog.dismiss();

			zipcode.setText(zip);
			address.setText(add + "," + sub2);
			area.setText(sub1);
			mcity.setText(city);

			// set value
			dish_name.setText(dishname + "+" + chkname);
			dish_name.setTypeface(tf2);
			dish_price.setText("Rs " + price);
			dish_price.setTypeface(tf2);
			total_price.setText("Rs " + price);
			total_price.setTypeface(tf2);
			supportMap.addMarker(new MarkerOptions()
					.position(current_latlng)
					.title(sub2)
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.location)));

			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(current_latlng) // Sets the center of the map to
											// Golden Gate Bridge
					.zoom(17) // Sets the zoom
					.bearing(90) // Sets the orientation of the camera to
									// east
					.tilt(30) // Sets the tilt of the camera to 30 degrees
					.build(); // Creates a CameraPosition from the builder
			supportMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));

			// Log.e("bbbbb", address);
		}
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
					obj.put("address", address.getText().toString());
					obj.put("area", area.getText().toString());
					obj.put("city", mcity.getText().toString());
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

					entity.addPart("data[Address][customer_id]",
							new StringBody(user_id));
					entity.addPart("data[Address][data]",
							new StringBody(obj.toString()));
					entity.addPart("data[Address][status]", new StringBody("1"));

					entity.addPart("data[Address][f_name]", new StringBody(
							fname.getText().toString()));
					entity.addPart("data[Address][l_name]", new StringBody(
							lname.getText().toString()));
					entity.addPart("data[Address][address]", new StringBody(
							address.getText().toString()));

					entity.addPart("data[Address][area]", new StringBody(area
							.getText().toString()));
					entity.addPart("data[Address][city]", new StringBody(mcity
							.getText().toString()));
					entity.addPart("data[Address][zipcode]", new StringBody(
							zipcode.getText().toString()));

					entity.addPart("data[Address][phone_number]",
							new StringBody(phone_num.getText().toString()));
					entity.addPart("data[Address][lat]",
							new StringBody(String.valueOf(latitude)));
					entity.addPart("data[Address][long]",
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
					String msg = obj1.getString("msg");
					String address_id = obj1.getString("addressid");
					if (msg.equals("success")) {
						String add = address.getText().toString() + ","
								+ area.getText().toString() + ","
								+ mcity.getText().toString() + ","
								+ zipcode.getText().toString();
						dialogd(add, address_id);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		};
		if ((DetectNetwork.hasConnection(getApplicationContext())))
			updateTask.execute((Void[]) null);

	}

	public void dialogd(String addss, final String addid) {

		final Dialog d = new Dialog(this);
		d.requestWindowFeature(Window.FEATURE_NO_TITLE);
		d.setContentView(R.layout.confirmation_dialog);
		TextView dish_name = (TextView) d.findViewById(R.id.textView4);
		TextView dish_price = (TextView) d.findViewById(R.id.textView3);
		TextView dish_add = (TextView) d.findViewById(R.id.textView5);
		TextView payment_type = (TextView) d.findViewById(R.id.textView8);
		Button cls = (Button) d.findViewById(R.id.button1);
		Button confirm = (Button) d.findViewById(R.id.button2);
		d.show();

		dish_name.setText(dishname + "+" + chkname);
		dish_price.setText("Rs " + price);
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
				orderProcess(addid);

			}
		});

	}

	// method for sending user detail on server
	protected void orderProcess(final String addid) {
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
					String st = obj.getString("data");
					if (st.equals("Success")) {
						Intent in = new Intent(OrderDishes.this,
								PaymentProcess.class);
						startActivity(in);
					}
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
