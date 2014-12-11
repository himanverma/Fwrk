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
import com.future.getfood.AnalyticsSampleApp.TrackerName;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
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
import android.text.Editable;
import android.text.TextWatcher;
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
	ImageView location_finder;
	TextView dish_name;
	TextView dish_price;
	TextView total_price;
	LatLng current_latlng;
	GPSTracker gps;
	double latitude, longitude;
	String add, city, country, sub1, sub2, state, zip;

	EditText fname, lname, address, area, zipcode, mcity, phone_num;
	CheckBox cod, debit;
	String chk_value = "na";
	Button save;
	SessionManager sess;
	String user_id;
	protected HttpResponse response;
	protected String s;
	private Typeface tf1, tf2, tf3, tf4;
	RelativeLayout rel6;
	String f_name, l_name, address1, area1, zipcode1, mcity1, phonenum;
	String chk;

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

		HashMap<String, String> umap = sess.getUserAddress();
		f_name = umap.get(SessionManager.KEY_FNAME);
		l_name = umap.get(SessionManager.KEY_LNAME);
		address1 = umap.get(SessionManager.KEY_ADDRESS);
		area1 = umap.get(SessionManager.KEY_AREA);
		zipcode1 = umap.get(SessionManager.KEY_ZIPCODE);
		mcity1 = umap.get(SessionManager.KEY_CITY);
		phonenum = umap.get(SessionManager.KEY_PHONEMOB);

		// getting value from MyAdapter
		Intent in = getIntent();
		dishname = in.getStringExtra("dish");
		chkname = in.getStringExtra("chk");
		price = in.getStringExtra("price");
		cid = in.getStringExtra("cid");
		chk = in.getStringExtra("chkk");

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

		fname.setText(f_name);
		lname.setText(l_name);
		address.setText(address1);
		area.setText(area1);
		mcity.setText(mcity1);
		zipcode.setText(zipcode1);
		phone_num.setText(phonenum);
		location_finder = (ImageView) findViewById(R.id.imageView4);

		phone_num.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (phone_num.getText().length() > 0) {
					phone_num.setError(null);
				}
			}
		});

		zipcode.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (zipcode.getText().length() > 0) {
					zipcode.setError(null);
				}
			}
		});

		mcity.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (mcity.getText().length() > 0) {
					mcity.setError(null);
				}
			}
		});

		area.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (area.getText().length() > 0) {
					area.setError(null);
				}
			}
		});

		address.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (address.getText().length() > 0) {
					address.setError(null);
				}
			}
		});

		fname.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (fname.getText().length() > 0) {
					fname.setError(null);
				}
			}
		});

		lname.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (lname.getText().length() > 0) {
					lname.setError(null);
				}
			}
		});

		// google analytics
		Tracker t = ((AnalyticsSampleApp) OrderDishes.this.getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName("OrderDishes");
		t.send(new HitBuilders.AppViewBuilder().build());

		// cls = (Button) findViewById(R.id.imageView4);

		save = (Button) findViewById(R.id.button1);
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

		cod.setTypeface(tf2);
		debit.setTypeface(tf2);

		rel6 = (RelativeLayout) findViewById(R.id.rel6);

		((RelativeLayout) findViewById(R.id.nnn2))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (user_id.equals("0")) {
							Toast.makeText(getApplicationContext(),
									"Make your profile first.", 5000).show();
						} else {

							Tracker t = ((AnalyticsSampleApp) OrderDishes.this
									.getApplication())
									.getTracker(TrackerName.APP_TRACKER);
							// Build and send an Event.
							t.send(new HitBuilders.EventBuilder()
									.setCategory("User Profile") // category
																	// i.e.
																	// Player
																	// Buttons
									.setAction("Check own profile") // action
																	// i.e. Play
									.setLabel("clicked") // label i.e. any
															// meta-data
									.build());

							Intent in = new Intent(OrderDishes.this,
									UserProfile.class);
							startActivity(in);
						}
					}
				});

		// set map position

		gps = new GPSTracker(OrderDishes.this);
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

		// set value
		dish_name.setText(dishname + "+" + chkname);
		dish_name.setTypeface(tf1);
		dish_price.setText("Rs " + price);
		dish_price.setTypeface(tf1);
		total_price.setText("Rs " + price);
		total_price.setTypeface(tf1);
		supportMap
				.addMarker(new MarkerOptions()
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

		location_finder.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Tracker t = ((AnalyticsSampleApp) OrderDishes.this
						.getApplication()).getTracker(TrackerName.APP_TRACKER);
				// Build and send an Event.
				t.send(new HitBuilders.EventBuilder()
						.setCategory("Getting Location") // category i.e. Player
															// Buttons
						.setAction("Getting own current location") // action
																	// i.e. Play
						.setLabel("clicked") // label i.e. any meta-data
						.build());

				GetCurrentAddress currentadd = new GetCurrentAddress();
				currentadd.execute();
			}
		});

		cod.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub

				if (cod.isChecked()) {

					debit.setChecked(false);
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
					chk_value = "Online Payment";
				}
			}
		});

		((RelativeLayout) findViewById(R.id.nnn1))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

//						if (chk.equals("notsearchlist")) {
//							// Intent in=new
//							// Intent(OrderDishes.this,DishHome.class);
//							// startActivity(in);
//
//							finish();
//						} else {
//
//							finish();
//						}
						
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

					fname.setError("Please fill first name!");
					fname.requestFocus();
				} else {
					if (lname.getText().toString().equals("")) {

						lname.setError("Please fill last name!");
						lname.requestFocus();
					} else {

						if (address.getText().toString().equals("")) {

							address.setError("Please fill address!");
							address.requestFocus();
						} else {

							if (area.getText().toString().equals("")) {

								area.setError("please fill area name!");
								area.requestFocus();
							} else {

								if (mcity.getText().toString().equals("")) {

									mcity.setError("Please fill city name!");
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

													// Tracker t =
													// ((AnalyticsSampleApp)
													// getApplicationContext()).getTracker(
													// TrackerName.APP_TRACKER);
													// t.setScreenName("OrderDishes make order");
													// t.send(new
													// HitBuilders.AppViewBuilder().build());

													sess.setUserDetail(
															fname.getText()
																	.toString(),
															lname.getText()
																	.toString(),
															address.getText()
																	.toString(),
															area.getText()
																	.toString(),
															mcity.getText()
																	.toString(),
															zipcode.getText()
																	.toString(),
															phone_num.getText()
																	.toString());

													Tracker t = ((AnalyticsSampleApp) OrderDishes.this
															.getApplication())
															.getTracker(TrackerName.APP_TRACKER);
													// Build and send an Event.
													t.send(new HitBuilders.EventBuilder()
															.setCategory(
																	"Order process") // category
																						// i.e.
																						// Player
																						// Buttons
															.setAction(
																	"Go for order") // action
																					// i.e.
																					// Play
															.setLabel("clicked") // label
																					// i.e.
																					// any
																					// meta-data
															.build());

												if(user_id.equals("0")){
													
													Intent in = new Intent(OrderDishes.this,
															UserLogin.class);
													in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
													in.putExtra("dish", dishname);
													in.putExtra("chk", chkname);
													in.putExtra("price",price);
													in.putExtra("cid", cid);
													
													in.putExtra("fname", fname.getText()
															.toString());
													in.putExtra("lname", lname.getText()
															.toString());
													in.putExtra("address",address.getText()
															.toString());
													in.putExtra("area", area.getText()
															.toString());
													
													in.putExtra("mcity", mcity.getText()
															.toString());
													in.putExtra("zipcode", zipcode.getText()
															.toString());
													in.putExtra("phone_num",phone_num.getText()
															.toString());
													
													in.putExtra("chk_value",chk_value);
													
													startActivity(in);
												}else{
													
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

			}
		});
	}

	
	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		
//		sess = new SessionManager(this);
//		HashMap<String, String> map = sess.getUserDetails();
//		user_id = map.get(SessionManager.KEY_ID);
//		
//		HashMap<String, String> umap = sess.getUserAddress();
//		f_name = umap.get(SessionManager.KEY_FNAME);
//		l_name = umap.get(SessionManager.KEY_LNAME);
//		address1 = umap.get(SessionManager.KEY_ADDRESS);
//		area1 = umap.get(SessionManager.KEY_AREA);
//		zipcode1 = umap.get(SessionManager.KEY_ZIPCODE);
//		mcity1 = umap.get(SessionManager.KEY_CITY);
//		phonenum = umap.get(SessionManager.KEY_PHONEMOB);
//
//	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
//
//		if (chk.equals("notsearchlist")) {
//			Intent in = new Intent(OrderDishes.this, DishHome.class);
//			startActivity(in);
//			finish();
//		} else {

			finish();
		//}
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

			sess.setUserDetail(fname.getText().toString(), lname.getText()
					.toString(), address.getText().toString(), area.getText()
					.toString(), mcity.getText().toString(), zipcode.getText()
					.toString(), phone_num.getText().toString());
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
				dialog.setCancelable(false);
				dialog.show();
			}

			@Override
			protected Void doInBackground(Void... params) {

				// do your background operation here

				JSONObject obj = new JSONObject();
				try {
					obj.put("firstname", fname.getText().toString());
					obj.put("lastname", lname.getText().toString());
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

		dish_user.setText(fname.getText().toString() + " "
				+ lname.getText().toString());
		dish_mob.setText(phone_num.getText().toString());

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

					Intent in = new Intent(OrderDishes.this,
							PaymentProcess.class);
					in.putExtra("url", "cod");
					startActivity(in);

				} else {

					orderProcess(addid);
				}

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

					Intent in = new Intent(OrderDishes.this,
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
