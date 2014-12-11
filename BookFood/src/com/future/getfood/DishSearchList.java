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
import android.view.WindowManager;
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

import com.future.foodimg.DetectNetwork;
import com.future.foodimg.ImageLoader;
import com.future.foodimg.LetterSpacingTextView;
import com.future.getfood.AnalyticsSampleApp.TrackerName;
import com.future.getfood.DishHome.ExpandableListAdapter;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class DishSearchList extends Activity {

	ExpandableListView expListView;
	ArrayList<HashMap<String, String>> listDataHeader = new ArrayList<HashMap<String, String>>();
	HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
	ExpandableListAdapter listAdapter;
	ImageLoader il;
	protected int lastExpandedPosition;
	private Typeface tf1, tf2, tf3, tf4;
	HttpResponse response;
	String s, st;
	EditText search;
	GPSTracker gps;
	ArrayList<String> address_list = new ArrayList<String>();
	ArrayList<String> phone_list = new ArrayList<String>();
	ArrayList<String> mobile_list = new ArrayList<String>();
	ArrayList<String> mylist = new ArrayList<String>();
	ArrayList<String> namelist = new ArrayList<String>();
	ArrayList<String> pricelist = new ArrayList<String>();
	ArrayList<String> imglist = new ArrayList<String>();
	ArrayList<String> chef_photo = new ArrayList<String>();
	ArrayList<String> chef_id = new ArrayList<String>();
	ArrayList<String> chef_rating = new ArrayList<String>();
	ArrayList<String> cid_list = new ArrayList<String>();
	String search1;
	SessionManager sess;
	String userid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.dish_search_list);

		
		tf1 = Typeface.createFromAsset(this.getAssets(), "Roboto-Bold.ttf");
		tf2 = Typeface.createFromAsset(this.getAssets(), "Roboto-Light.ttf");
		tf3 = Typeface.createFromAsset(this.getAssets(), "Roboto-Regular.ttf");
		tf4 = Typeface.createFromAsset(this.getAssets(), "Roboto-Thin.ttf");
		
		il=new ImageLoader(this);
		
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		search = (EditText) findViewById(R.id.editText1);
		//get intent value
		Intent in = getIntent();
		search1 = in.getStringExtra("search");
		getdetail(search1);
//
//		sess = new SessionManager(this);
//		HashMap<String, String> map = sess.getUserDetails();
//		userid = map.get(SessionManager.KEY_ID);

		Tracker t = ((AnalyticsSampleApp) DishSearchList.this.getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName("DishSearchList for filter dishes");
		t.send(new HitBuilders.AppViewBuilder().build());

		((RelativeLayout) findViewById(R.id.nn2))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (userid.equals("0")) {
							Toast.makeText(getApplicationContext(),
									"Make your profile first.", 5000).show();
						} else {

							Tracker t = ((AnalyticsSampleApp) DishSearchList.this
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
							Intent in = new Intent(DishSearchList.this,
									UserProfile.class);
							startActivity(in);
						}
					}
				});

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
						finish();
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
//						Toast.makeText(getApplicationContext(),
//								listDataHeader.get(groupPosition) + " Expanded",
//								Toast.LENGTH_SHORT).show();

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
//						Toast.makeText(getApplicationContext(),
//								listDataHeader.get(groupPosition) + " Collapsed",
//								Toast.LENGTH_SHORT).show();

					}
				});

				// Listview on child click listener
				expListView.setOnChildClickListener(new OnChildClickListener() {

					@Override
					public boolean onChildClick(ExpandableListView parent, View v,
							int groupPosition, int childPosition, long id) {
						// TODO Auto-generated method stub
//						Toast.makeText(
//								getApplicationContext(),
//								listDataHeader.get(groupPosition)
//										+ " : "
//										+ listDataChild.get(
//												listDataHeader.get(groupPosition)).get(
//												childPosition), Toast.LENGTH_SHORT)
//								.show();
						return false;
					}
				});
				
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
	protected void getdetail(final String search) {
		// TODO Auto-generated method stub

		AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
			ProgressDialog dialog = new ProgressDialog(DishSearchList.this);

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
							+ "api/combinations/search.json?a=" + milli;
					mylist.clear();
					MultipartEntity entity = new MultipartEntity(
							HttpMultipartMode.BROWSER_COMPATIBLE);

					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(url);
					//
					entity.addPart("data[Combination][search]", new StringBody(
							search));
					httppost.setEntity(entity);

					response = httpclient.execute(httppost);

					s = EntityUtils.toString(response.getEntity());
					//Log.e("fhgfhj", s);

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
					JSONArray arr = obj.getJSONArray("data");
					if (arr.length() > 0) {
						for (int i = 0; i < arr.length(); i++) {

							JSONObject obj1 = arr.getJSONObject(i);
							JSONObject obj2 = obj1.getJSONObject("Combination");
							String display_name = obj2
									.getString("display_name");
							String image = obj2.getString("image");
							String cid = obj2.getString("id");
							String price = obj2.getString("price");

							JSONObject obj3 = obj1.getJSONObject("Vendor");
							String id = obj3.getString("id");
							String name = obj3.getString("name");
							String photo = obj3.getString("photo");
							String company_logo = obj3
									.getString("company_logo");
							String company_name = obj3
									.getString("company_name");
							String address = obj3.getString("address");
							String city = obj3.getString("city");
							String state = obj3.getString("state");
							String country = obj3.getString("country");
							String email = obj3.getString("email");
							String mobile_number = obj3
									.getString("mobile_number");
							String phone_number = obj3
									.getString("phone_number");
							String lat = obj3.getString("lat");
							String lng = obj3.getString("long");
							String rating = obj3.getString("ratings");
							// Log.e("nnn", display_name+"   "+name);

							mylist.add(display_name);
							namelist.add(name);
							pricelist.add(price);
							imglist.add(image);
							address_list.add(address + "," + city);
							phone_list.add(phone_number);
							mobile_list.add(mobile_number);
							chef_photo.add(photo);
							chef_id.add(id);
							chef_rating.add(rating);
							cid_list.add(cid);
							
							List<String> checkbox = new ArrayList<String>();
							checkbox.add("Full Rice");
							
							listDataChild.put(mylist.get(i), checkbox); 
							
						}

//						adapter = new MyAdapter(DishSearchList.this, mylist,
//								namelist, pricelist, imglist, address_list,
//								phone_list, mobile_list, chef_photo, chef_id,
//								chef_rating, cid_list);
//						listView.setAdapter(adapter);
						
						
						listAdapter = new ExpandableListAdapter(DishSearchList.this, mylist, listDataChild);
						// setting list adapter
						expListView.setAdapter(listAdapter);
						
					} else {

						// Toast.makeText(getApplicationContext(),
						// "No data found", 5000).show();
						alert("No data found");
					}
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
				DishSearchList.this).create();

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
	
	
	
	public class ExpandableListAdapter extends BaseExpandableListAdapter {

		private Context _context;
		private ArrayList<HashMap<String, String>> _listDataHeader; // header titles
		private ArrayList<HashMap<String, String>> alldata; // header titles
		// child data in format of header title, child title
		private HashMap<String, List<String>> _listDataChild;
		private String chk_value;
		ArrayList<String> list;
		ArrayList<String> namelist1;
		
		public ExpandableListAdapter(Context context,  ArrayList<String> listDataHeader,
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
			return this._listDataChild.get(this.list.get(groupPosition))
					.get(childPosititon);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(final int groupPosition, final int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {

			final String childText = (String) getChild(groupPosition, childPosition);
			
			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.list_item, null);
			}

			final Button send = (Button) convertView.findViewById(R.id.button2);
			final CheckBox chk1 = (CheckBox) convertView.findViewById(R.id.checkBox1);
			final CheckBox chk2 = (CheckBox) convertView.findViewById(R.id.checkBox2);
			final CheckBox chk3 = (CheckBox) convertView.findViewById(R.id.checkBox3);
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

						Toast.makeText(DishSearchList.this, "please select option!", 5000).show();

					} else {

//						if (userid.equals("0")) {
//
//							Intent in = new Intent(DishSearchList.this, UserLogin.class);
//							in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//							in.putExtra("dish", list.get(groupPosition));
//							in.putExtra("chk", chk_value);
//							in.putExtra("price", pricelist.get(groupPosition));
//							in.putExtra("cid", cid_list.get(groupPosition));
//							startActivity(in);
//						
//							 Tracker t = ((AnalyticsSampleApp) DishSearchList.this.getApplicationContext()).getTracker(
//						               TrackerName.APP_TRACKER);
//							// Build and send an Event.
//								t.send(new HitBuilders.EventBuilder()
//								    .setCategory("Login Form")  // category i.e. Player Buttons
//								    .setAction("Go for making Login and signup")    // action i.e.  Play
//								    .setLabel("clicked")    // label i.e.  any meta-data
//								    .build());
//							
//						
//						} else {
							
							Intent in = new Intent(DishSearchList.this, OrderDishes.class);
							in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							in.putExtra("dish", list.get(groupPosition));
							in.putExtra("chk", chk_value);
							in.putExtra("price", pricelist.get(groupPosition));
							in.putExtra("cid", cid_list.get(groupPosition));
							in.putExtra("chkk", "searchlist");
							startActivity(in);
						
							 Tracker t = ((AnalyticsSampleApp) DishSearchList.this.getApplicationContext()).getTracker(
						               TrackerName.APP_TRACKER);
							// Build and send an Event.
								t.send(new HitBuilders.EventBuilder()
								    .setCategory("Order")  // category i.e. Player Buttons
								    .setAction("GO for making order")    // action i.e.  Play
								    .setLabel("clicked")    // label i.e.  any meta-data
								    .build());
						       
						}
					//}
				}
			});

			
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return this._listDataChild.get(this.list.get(groupPosition))
					.size();
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
			//String headerTitle = (String) getGroup(groupPosition);
			
			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.list_group, null);
			}

			LetterSpacingTextView  dish_name = ((LetterSpacingTextView) convertView.findViewById(R.id.dishname));
			ImageView dish_img = (ImageView) convertView.findViewById(R.id.imageView1);
			TextView user_name = ((TextView) convertView.findViewById(R.id.textView1));
			RatingBar rb = (RatingBar) convertView.findViewById(R.id.ratingBar1);
			TextView rate_num = ((TextView) convertView.findViewById(R.id.textView2));
			TextView delivery_time = ((TextView) convertView.findViewById(R.id.textView3));
			TextView price_dd = ((TextView) convertView.findViewById(R.id.textView4));

			
			// set value
			il.DisplayImage(imglist.get(groupPosition), dish_img);
			dish_name.setText(mylist.get(groupPosition));
			dish_name.setTypeface(tf3);
			dish_name.setLetterSpacing(-3); 
			user_name.setText("By " + namelist.get(groupPosition));
			user_name.setTypeface(tf2);
			price_dd.setText("Price: Rs " +pricelist.get(groupPosition));
			price_dd.setTypeface(tf1);
			delivery_time.setTypeface(tf4);
			rb.setRating(Float.parseFloat(chef_rating.get(groupPosition)));
			rate_num.setText("("+chef_rating.get(groupPosition)+")");
			rate_num.setTypeface(tf4);
			
			
			user_name.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

							
					Intent in = new Intent(DishSearchList.this, ChefProfile.class);
					in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					in.putExtra("photo", chef_photo.get(groupPosition));
					in.putExtra("name", namelist.get(groupPosition));
					in.putExtra("address", address_list.get(groupPosition));
					in.putExtra("mob", mobile_list.get(groupPosition));
					in.putExtra("phone", phone_list.get(groupPosition));
					in.putExtra("chefid", chef_id.get(groupPosition));
					in.putExtra("rate", chef_rating.get(groupPosition));
					startActivity(in);
					
					 Tracker t = ((AnalyticsSampleApp) DishSearchList.this.getApplicationContext()).getTracker(
				               TrackerName.APP_TRACKER);
					// Build and send an Event.
						t.send(new HitBuilders.EventBuilder()
						    .setCategory("Chef Profile")  // category i.e. Player Buttons
						    .setAction("Check Chef profile")    // action i.e.  Play
						    .setLabel("clicked")    // label i.e.  any meta-data
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
