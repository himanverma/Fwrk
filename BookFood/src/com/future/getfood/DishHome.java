package com.future.getfood;

import java.io.IOException;
import java.util.ArrayList;
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
import org.json.JSONArray;
import org.json.JSONObject;

import com.future.foodimg.DetectNetwork;
import com.future.foodimg.ImageLoader;
import com.future.foodimg.LetterSpacingTextView;
import com.future.getfood.AnalyticsSampleApp.TrackerName;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class DishHome extends Activity {

	ExpandableListView expListView;
	ArrayList<HashMap<String, String>> listDataHeader = new ArrayList<HashMap<String, String>>();
	HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
	private boolean executing = false;
	ArrayList<HashMap<String, String>> items;
	HttpResponse response;
	String s, st;
	EditText search;
	GPSTracker gps;
	double latitude, longitude;
	RelativeLayout nn1;
	ImageView profile;
	ArrayList<String> mylist = new ArrayList<String>();
	ArrayList<String> namelist = new ArrayList<String>();
	ArrayList<String> pricelist = new ArrayList<String>();
	ArrayList<String> imglist = new ArrayList<String>();
	ArrayList<String> address_list = new ArrayList<String>();
	ArrayList<String> phone_list = new ArrayList<String>();
	ArrayList<String> mobile_list = new ArrayList<String>();
	ArrayList<String> chef_photo = new ArrayList<String>();
	ArrayList<String> chef_id = new ArrayList<String>();
	ArrayList<String> chef_rating = new ArrayList<String>();
	ArrayList<String> cid_list = new ArrayList<String>();
	SessionManager sess;
	String userid;
	Tracker t;
	protected int total_num;
	Boolean isInternetPresent = false;
	ExpandableListAdapter listAdapter;
	ImageLoader il;
	protected int lastExpandedPosition;
	private Typeface tf1, tf2, tf3, tf4;

	RelativeLayout rel1, rel2;
	ConnectionDetector cd;
	AlertDialogManager alert = new AlertDialogManager();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.expandlistview);

		cd = new ConnectionDetector(getApplicationContext());

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			alert("No Internet connection found.");
		} else {

		// Google analytics

		tf1 = Typeface.createFromAsset(this.getAssets(), "Roboto-Bold.ttf");
		tf2 = Typeface.createFromAsset(this.getAssets(), "Roboto-Light.ttf");
		tf3 = Typeface.createFromAsset(this.getAssets(), "Roboto-Regular.ttf");
		tf4 = Typeface.createFromAsset(this.getAssets(), "Roboto-Thin.ttf");

		il = new ImageLoader(this);
		t = ((AnalyticsSampleApp) DishHome.this.getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName("DishHome get all dishes");
		t.send(new HitBuilders.AppViewBuilder().build());

//		sess = new SessionManager(this);
//		HashMap<String, String> map = sess.getUserDetails();
//		userid = map.get(SessionManager.KEY_ID);

		search = (EditText) findViewById(R.id.editText1);
		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		rel1 = (RelativeLayout) findViewById(R.id.rel1);
		rel2 = (RelativeLayout) findViewById(R.id.main);

		profile = (ImageView) findViewById(R.id.imageView2);
		((RelativeLayout) findViewById(R.id.nn2))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (userid.equals("0")) {
							Toast.makeText(getApplicationContext(),
									"Make your profile first.", 5000).show();
						} else {

							// Build and send an Event.
							t.send(new HitBuilders.EventBuilder()
									.setCategory("Profile") // category i.e.
															// Player Buttons
									.setAction("Check own profile") // action
																	// i.e. Play
									.setLabel("clicked") // label i.e. any
															// meta-data
									.build());

							Intent in = new Intent(DishHome.this,
									UserProfile.class);
							startActivity(in);
						}
					}
				});
		// listView.setListener(scrollListener);

		// getting current latlng
		gps = new GPSTracker(DishHome.this);
		if (gps.canGetLocation()) {

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

			getdetail(1);

		} else {

			gps.showSettingsAlert();
		}

		search.addTextChangedListener(new TextWatcher() {

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
				String text = search.getText().toString()
						.toLowerCase(Locale.getDefault());

				try {
					listAdapter.filter(text);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		((RelativeLayout) findViewById(R.id.nn1))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						// Build and send an Event.
						t.send(new HitBuilders.EventBuilder()
								.setCategory("DishActivity") // category i.e.
																// Player
																// Buttons
								.setAction("Check dishes filter list") // action
																		// i.e.
																		// Play
								.setLabel("clicked") // label i.e. any meta-data
								.build());

						Intent in = new Intent(DishHome.this, DishesList.class);
						startActivity(in);

					}
				});

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				// Toast.makeText(getApplicationContext(),
				// listDataHeader.get(groupPosition) + " Expanded",
				// Toast.LENGTH_SHORT).show();

				if (lastExpandedPosition != -1
						&& groupPosition != lastExpandedPosition) {
					expListView.collapseGroup(lastExpandedPosition);
				}
				lastExpandedPosition = groupPosition;
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				// Toast.makeText(getApplicationContext(),
				// listDataHeader.get(groupPosition) + " Collapsed",
				// Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				// Toast.makeText(
				// getApplicationContext(),
				// listDataHeader.get(groupPosition)
				// + " : "
				// + listDataChild.get(
				// listDataHeader.get(groupPosition)).get(
				// childPosition), Toast.LENGTH_SHORT)
				// .show();
				return false;
			}
		});

	}

	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		sess = new SessionManager(this);
		HashMap<String, String> map = sess.getUserDetails();
		userid = map.get(SessionManager.KEY_ID);
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
	protected void getdetail(final int page) {
		// TODO Auto-generated method stub

		AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
			// ProgressDialog dialog = new ProgressDialog(DishHome.this);

			@Override
			protected void onPreExecute() {
				// what to do before background task
				// dialog.setMessage("Validating... ");
				// dialog.setIndeterminate(true);
				// dialog.show();

				rel1.setVisibility(View.GONE);
				rel2.setVisibility(View.VISIBLE);
				expListView.setVisibility(View.GONE);
			}

			@Override
			protected Void doInBackground(Void... params) {

				// do your background operation here
				try {
					long milli = System.currentTimeMillis();
					String url = getResources().getString(R.string.url)
							+ "api/Combinations.json?a=" + milli;
					mylist.clear();
					MultipartEntity entity = new MultipartEntity(
							HttpMultipartMode.BROWSER_COMPATIBLE);

					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(url);

					entity.addPart("data[User][latitude]", new StringBody(
							String.valueOf(latitude)));
					entity.addPart("data[User][longitude]", new StringBody(
							String.valueOf(longitude)));
					entity.addPart("data[User][count]",
							new StringBody(String.valueOf(page)));

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

				try {

					HashMap<String, String> map = new HashMap<String, String>();

					JSONObject obj = new JSONObject(s);
					JSONObject data = obj.getJSONObject("data");
					JSONArray arr=data.getJSONArray("items");

				if(arr.length()>0){
					
					for(int i=0;i<arr.length();i++){
						
						JSONObject obj1=arr.getJSONObject(i);
						JSONObject obj2=obj1.getJSONObject("0");
						String distance=obj2.getString("distance");
						
						JSONObject obj4 = obj1.getJSONObject("Vendor");
						String id = obj4.getString("id");
						String name = obj4.getString("name");
						String photo = obj4.getString("photo");
						String company_logo = obj4
								.getString("company_logo");
						String company_name = obj4
								.getString("company_name");
						String address = obj4.getString("address");
						String city = obj4.getString("city");
						String state = obj4.getString("state");
						String country = obj4.getString("country");
						String email = obj4.getString("email");
						String mobile_number = obj4
								.getString("mobile_number");
						String phone_number = obj4
								.getString("phone_number");
						String lat = obj4.getString("lat");
						String lng = obj4.getString("long");
						String rating = obj4.getString("ratings");
						
						JSONObject obj5 = obj1.getJSONObject("Combination");
						String vendor_name = obj5.getString("display_name");
						String price = obj5.getString("price");
						String cmd_id = obj5.getString("id");
						String image = obj5.getString("image");
						
						mylist.add(vendor_name);
						namelist.add(name);
						pricelist.add(price);
						imglist.add(image);
						address_list.add(address + "," + city);
						phone_list.add(phone_number);
						mobile_list.add(mobile_number);
						chef_photo.add(photo);
						chef_id.add(id);
						chef_rating.add(rating);
						cid_list.add(cmd_id);
						
						List<String> checkbox = new ArrayList<String>();
						checkbox.add("Full Rice");

						listDataChild.put(mylist.get(i), checkbox);

						
					}
					
					} else {

						alert("no data found");
					}
				} catch (Exception e) {

					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// what to do when background task is completed

				listAdapter = new ExpandableListAdapter(DishHome.this, mylist,
						listDataChild);
				// setting list adapter
				expListView.setAdapter(listAdapter);

				// dialog.cancel();

				rel1.setVisibility(View.VISIBLE);
				rel2.setVisibility(View.GONE);
				expListView.setVisibility(View.VISIBLE);
			}

		};
		if ((DetectNetwork.hasConnection(getApplicationContext())))
			updateTask.execute((Void[]) null);

	}

	@SuppressWarnings("deprecation")
	public void alert(String msg) {

		final AlertDialog alertDialog = new AlertDialog.Builder(DishHome.this)
				.create();

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

	// ExpandableListAdapter class for setting value

	public class ExpandableListAdapter extends BaseExpandableListAdapter {

		private Context _context;
		private ArrayList<HashMap<String, String>> _listDataHeader; // header
																	// titles
		private ArrayList<HashMap<String, String>> alldata; // header titles
		// child data in format of header title, child title
		private HashMap<String, List<String>> _listDataChild;
		private String chk_value;
		ArrayList<String> list;
		ArrayList<String> namelist1;

		public ExpandableListAdapter(Context context,
				ArrayList<String> listDataHeader,
				HashMap<String, List<String>> listChildData) {

			list = new ArrayList<String>();
			namelist1 = new ArrayList<String>();

			this._context = context;
			this.list = listDataHeader;
			this._listDataChild = listChildData;

			namelist1.addAll(list);
		}

		@Override
		public Object getChild(int groupPosition, int childPosititon) {
			return this._listDataChild.get(this.list.get(groupPosition)).get(
					childPosititon);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(final int groupPosition,
				final int childPosition, boolean isLastChild, View convertView,
				ViewGroup parent) {

			final String childText = (String) getChild(groupPosition,
					childPosition);

			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.list_item, null);
			}

			final Button send = (Button) convertView.findViewById(R.id.button2);
			final CheckBox chk1 = (CheckBox) convertView
					.findViewById(R.id.checkBox1);
			final CheckBox chk2 = (CheckBox) convertView
					.findViewById(R.id.checkBox2);
			final CheckBox chk3 = (CheckBox) convertView
					.findViewById(R.id.checkBox3);
			chk2.setChecked(true);
			chk_value = "4 Roti+Half Rice";

			chk1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					// TODO Auto-generated method stub

					if (chk1.isChecked()) {

						chk_value = "Full Rice";
						chk2.setChecked(false);
						chk3.setChecked(false);

					}
				}
			});

			chk2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					// TODO Auto-generated method stub

					if (chk2.isChecked()) {

						chk_value = "4 Roti+Half Rice";
						chk1.setChecked(false);
						chk3.setChecked(false);

					}
				}
			});

			chk3.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					// TODO Auto-generated method stub

					if (chk3.isChecked()) {

						chk_value = "6 Roti";
						chk1.setChecked(false);
						chk2.setChecked(false);

					}
				}
			});

			send.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					if (chk_value.equals("no")) {

						Toast.makeText(DishHome.this, "please select option!",
								5000).show();

					} else {

//						if (userid.equals("0")) {
//
//							Intent in = new Intent(DishHome.this,
//									UserLogin.class);
//							in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//							in.putExtra("dish", list.get(groupPosition));
//							in.putExtra("chk", chk_value);
//							in.putExtra("price", pricelist.get(groupPosition));
//							in.putExtra("cid", cid_list.get(groupPosition));
//							in.putExtra("chkk", "notsearchlist");
//							startActivity(in);
//
//							Tracker t = ((AnalyticsSampleApp) DishHome.this
//									.getApplicationContext())
//									.getTracker(TrackerName.APP_TRACKER);
//							// Build and send an Event.
//							t.send(new HitBuilders.EventBuilder()
//									.setCategory("Login Form") // category i.e.
//																// Player
//																// Buttons
//									.setAction("Go for making Login and signup") // action
//																					// i.e.
//																					// Play
//									.setLabel("clicked") // label i.e. any
//															// meta-data
//									.build());
//
//						} else {

							if (chk1.isChecked() || chk2.isChecked()
									|| chk3.isChecked()) {

								Intent in = new Intent(DishHome.this,
										OrderDishes.class);
								in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								in.putExtra("dish", list.get(groupPosition));
								in.putExtra("chk", chk_value);
								in.putExtra("price",
										pricelist.get(groupPosition));
								in.putExtra("cid", cid_list.get(groupPosition));
								in.putExtra("chkk", "notsearchlist");
								 startActivity(in);
							
								// finish();
								Tracker t = ((AnalyticsSampleApp) DishHome.this
										.getApplicationContext())
										.getTracker(TrackerName.APP_TRACKER);
								// Build and send an Event.
								t.send(new HitBuilders.EventBuilder()
										.setCategory("Order") // category i.e.
																// Player
																// Buttons
										.setAction("GO for making order") // action
																			// i.e.
																			// Play
										.setLabel("clicked") // label i.e. any
																// meta-data
										.build());

							} else {

								Toast.makeText(getApplicationContext(),
										"Please select one option!", 5000)
										.show();
							}
						}
					}
				//}
			});

			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return this._listDataChild.get(this.list.get(groupPosition)).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return this.list.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return this.list.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(final int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// String headerTitle = (String) getGroup(groupPosition);

			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.list_group, null);
			}

			LetterSpacingTextView dish_name = ((LetterSpacingTextView) convertView
					.findViewById(R.id.dishname));
			ImageView dish_img = (ImageView) convertView
					.findViewById(R.id.imageView1);
			TextView user_name = ((TextView) convertView
					.findViewById(R.id.textView1));
			RatingBar rb = (RatingBar) convertView
					.findViewById(R.id.ratingBar1);
			TextView rate_num = ((TextView) convertView
					.findViewById(R.id.textView2));
			TextView delivery_time = ((TextView) convertView
					.findViewById(R.id.textView3));
			TextView price_dd = ((TextView) convertView
					.findViewById(R.id.textView4));

			// set value
			il.DisplayImage(imglist.get(groupPosition), dish_img);
			dish_name.setText(mylist.get(groupPosition));
			dish_name.setTypeface(tf3);
			dish_name.setLetterSpacing(-3);
			user_name.setText("By " + namelist.get(groupPosition));
			user_name.setTypeface(tf2);
			price_dd.setText("Price: Rs " + pricelist.get(groupPosition));
			price_dd.setTypeface(tf1);
			delivery_time.setTypeface(tf4);
			rb.setRating(Float.parseFloat(chef_rating.get(groupPosition)));
			rate_num.setText("(" + chef_rating.get(groupPosition) + ")");
			rate_num.setTypeface(tf4);

			user_name.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent in = new Intent(DishHome.this, ChefProfile.class);
					in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					in.putExtra("photo", chef_photo.get(groupPosition));
					in.putExtra("name", namelist.get(groupPosition));
					in.putExtra("address", address_list.get(groupPosition));
					in.putExtra("mob", mobile_list.get(groupPosition));
					in.putExtra("phone", phone_list.get(groupPosition));
					in.putExtra("chefid", chef_id.get(groupPosition));
					in.putExtra("rate", chef_rating.get(groupPosition));
					startActivity(in);

					Tracker t = ((AnalyticsSampleApp) DishHome.this
							.getApplicationContext())
							.getTracker(TrackerName.APP_TRACKER);
					// Build and send an Event.
					t.send(new HitBuilders.EventBuilder()
							.setCategory("Chef Profile") // category i.e. Player
															// Buttons
							.setAction("Check Chef profile") // action i.e. Play
							.setLabel("clicked") // label i.e. any meta-data
							.build());

				}
			});

			return convertView;
		}

		// Filter Class
		public void filter(String charText) {
			charText = charText.toLowerCase(Locale.getDefault());
			list.clear();
			if (charText.length() == 0) {
				list.addAll(namelist1);
			} else {
				for (int i = 0; i < namelist1.size(); i++) {
					if (namelist1.get(i).toLowerCase(Locale.getDefault())
							.contains(charText)) {
						list.add(namelist1.get(i));
					}
				}
			}
			notifyDataSetChanged();
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	
}
