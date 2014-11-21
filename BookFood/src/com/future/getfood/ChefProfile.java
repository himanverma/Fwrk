package com.future.getfood;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChefProfile extends Activity {

	ImageView back;
	String img_url;
	String chef_name;
	String chef_add;
	String chef_mob;
	String chef_pho, chef_id, chef_rate;
	ImageView chef_photo;
	TextView chefname;
	TextView chefadd;
	TextView chefmob;
	ImageLoader il;
	RelativeLayout dish_list, dish_review;
	ListView dishlist;
	protected HttpResponse response;
	protected String s;
	ArrayList<String> chefid = new ArrayList<String>();
	ArrayList<String> dishnamelist = new ArrayList<String>();
	ArrayList<String> dishimg = new ArrayList<String>();
	ArrayList<String> dishprice = new ArrayList<String>();
	ArrayList<String> vidlist = new ArrayList<String>();

	Reviewadapter adp;
	ArrayList<String> rcustmerid = new ArrayList<String>();
	ArrayList<String> rcustmername = new ArrayList<String>();
	ArrayList<String> rcustmerimg = new ArrayList<String>();
	ArrayList<String> rcustmerreview = new ArrayList<String>();
	ArrayList<String> rcustmerrate = new ArrayList<String>();
	ArrayList<String> rtime = new ArrayList<String>();
	ArrayList<String> rid = new ArrayList<String>();
	ArrayList<String> rvid = new ArrayList<String>();

	MyAdapter adapter;
	RatingBar bar;
	RelativeLayout rel_list, rel_review;
	private Typeface tf1, tf2, tf3, tf4;
	TextView t1;
	SessionManager sess;

	// for chef reviews
	ListView reviewlist;
	EditText txt;
	ImageView post;
	RatingBar rateme;
	String userid;
	float ratevalue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.chef_profile);

		sess = new SessionManager(this);
		HashMap<String, String> map = sess.getUserDetails();
		userid = map.get(SessionManager.KEY_ID);

		tf1 = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");
		tf2 = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
		tf3 = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
		tf4 = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");

		il = new ImageLoader(this);
		Intent in = getIntent();
		img_url = in.getStringExtra("photo");
		chef_name = in.getStringExtra("name");
		chef_add = in.getStringExtra("address");
		chef_mob = in.getStringExtra("mob");
		chef_pho = in.getStringExtra("phone");
		chef_id = in.getStringExtra("chefid");
		chef_rate = in.getStringExtra("rate");

		t1 = (TextView) findViewById(R.id.textView1);
		t1.setTypeface(tf1);

		reviewlist = (ListView) findViewById(R.id.listView1);
		txt = (EditText) findViewById(R.id.editText1);
		post = (ImageView) findViewById(R.id.imageView5);
		rateme = (RatingBar) findViewById(R.id.ratingBar3);

		chef_photo = (ImageView) findViewById(R.id.imageView2);
		chefname = (TextView) findViewById(R.id.textView2);
		chefname.setTypeface(tf2);
		chefadd = (TextView) findViewById(R.id.textView3);
		chefadd.setTypeface(tf3);
		chefmob = (TextView) findViewById(R.id.textView4);
		chefmob.setTypeface(tf3);

		dish_list = (RelativeLayout) findViewById(R.id.dish_list);
		dish_review = (RelativeLayout) findViewById(R.id.review_list);

		bar = (RatingBar) findViewById(R.id.ratingBar1);

		dishlist = (ListView) findViewById(R.id.listView2);

		rel_list = (RelativeLayout) findViewById(R.id.List_btn);
		rel_review = (RelativeLayout) findViewById(R.id.review);

		post.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String nnn = txt.getText().toString();
				if (nnn.equals("")) {

					Toast.makeText(getApplicationContext(), "fill review box",
							5000).show();

				} else {

					sendReview();
				}
			}
		});
		rateme.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub

				ratevalue = rating;
				
			}
		});

		rel_list.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rel_list.setBackgroundResource(R.drawable.active_btn);
				rel_review.setBackgroundResource(R.drawable.unactive_btn);

				dish_list.setVisibility(View.VISIBLE);
				dish_review.setVisibility(View.GONE);

				getdish();
			}
		});

		rel_review.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rel_list.setBackgroundResource(R.drawable.unactive_btn);
				rel_review.setBackgroundResource(R.drawable.active_btn);

				dish_list.setVisibility(View.GONE);
				dish_review.setVisibility(View.VISIBLE);
				getReview();
			}
		});

		// set values of chef
		getdish();

		bar.setRating(Float.parseFloat(chef_rate));

		if (img_url.equals(null)) {

			chef_photo.setImageResource(R.drawable.ic_launcher);
		} else {

			il.DisplayImage(img_url, chef_photo);
		}

		if (chef_name.equals(null)) {

			chefname.setText("Not set");
		} else {

			chefname.setText(chef_name);

		}

		if (chef_add.equals(null)) {

			chefadd.setText("Not set");
		} else {

			chefadd.setText(chef_add);

		}

		if (chef_mob.equals(null)) {

			chefmob.setText("Not set");

		} else {

			chefmob.setText(chef_mob);
		}

		back = (ImageView) findViewById(R.id.imageView1);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	@Override
	public void onBackPressed() {

		finish();
	}

	// method for sending user detail on server
	protected void getdish() {
		// TODO Auto-generated method stub

		AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
			ProgressDialog dialog = new ProgressDialog(ChefProfile.this);

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

				chefid.clear();
				vidlist.clear();
				dishimg.clear();
				dishnamelist.clear();
				dishprice.clear();

				try {
					long milli = System.currentTimeMillis();
					String url = getResources().getString(R.string.url)
							+ "api/vendors/view/" + chef_id + ".json?a="
							+ milli;

					MultipartEntity entity = new MultipartEntity(
							HttpMultipartMode.BROWSER_COMPATIBLE);

					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(url);

					httppost.setEntity(entity);

					response = httpclient.execute(httppost);

					s = EntityUtils.toString(response.getEntity());
				//	Log.e("fhgfhj", s);

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
					JSONArray arr = obj1.getJSONArray("Combination");
					for (int i = 0; i < arr.length(); i++) {
						JSONObject obj2 = arr.getJSONObject(i);
						String id = obj2.getString("id");
						String vendor_id = obj2.getString("vendor_id");
						String display_name = obj2.getString("display_name");
						String image = obj2.getString("image");
						String price = obj2.getString("price");
						chefid.add(id);
						vidlist.add(vendor_id);
						dishimg.add(image);
						dishnamelist.add(display_name);
						dishprice.add(price);
					}

					adapter = new MyAdapter(getApplicationContext(), chefid,
							dishnamelist, dishimg, dishprice, vidlist);

					dishlist.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					
				} catch (Exception e) {

					e.printStackTrace();
				}
				dialog.cancel();
			}

		};
		if ((DetectNetwork.hasConnection(getApplicationContext())))
			updateTask.execute((Void[]) null);

	}

	public class MyAdapter extends BaseAdapter {

		ArrayList<String> chefid = new ArrayList<String>();
		ArrayList<String> dishnamelist = new ArrayList<String>();
		ArrayList<String> dishimg = new ArrayList<String>();
		ArrayList<String> dishprice = new ArrayList<String>();
		ArrayList<String> vidlist = new ArrayList<String>();
		Context ctx;
		LayoutInflater inflater = null;
		ImageLoader il;

		public MyAdapter(Context context, ArrayList<String> l1,
				ArrayList<String> l2, ArrayList<String> l3,
				ArrayList<String> l4, ArrayList<String> l5) {
			ctx = context;
			chefid = l1;
			dishnamelist = l2;
			dishimg = l3;
			dishprice = l4;
			vidlist = l5;

			il = new ImageLoader(ctx);
			inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dishnamelist.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		public class Holder {
			TextView dish_name;
			TextView chef_name;
			TextView price;
			TextView rate;
			ImageView dish_img;
			RatingBar bar;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub

			Holder holder = new Holder();
			View rowView = arg1;

			rowView = inflater.inflate(R.layout.chef_dish_items, null);
			holder.dish_name = (TextView) rowView.findViewById(R.id.dishname);
			holder.chef_name = (TextView) rowView.findViewById(R.id.textView1);
			holder.price = (TextView) rowView.findViewById(R.id.textView4);
			holder.rate = (TextView) rowView.findViewById(R.id.textView2);
			holder.dish_img = (ImageView) rowView.findViewById(R.id.imageView1);
			holder.bar = (RatingBar) rowView.findViewById(R.id.ratingBar1);

			holder.dish_name.setText(dishnamelist.get(arg0));
			holder.dish_name.setTypeface(tf1);
			holder.chef_name.setText(chef_name);
			holder.chef_name.setTypeface(tf3);
			holder.rate.setText("(" + chef_rate + ")");
			holder.rate.setTypeface(tf1);
			holder.price.setText("Price :Rs" + dishprice.get(arg0));
			holder.price.setTypeface(tf1);
			il.DisplayImage(dishimg.get(arg0), holder.dish_img);
			holder.bar.setRating(Float.parseFloat(chef_rate));

			return rowView;
		}

	}

	// method for sending user detail on server
	protected void sendReview() {
		// TODO Auto-generated method stub

		AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
			ProgressDialog dialog = new ProgressDialog(ChefProfile.this);

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
							+ "api/VendorReviews/add.json?a=" + milli;

					MultipartEntity entity = new MultipartEntity(
							HttpMultipartMode.BROWSER_COMPATIBLE);

					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(url);

					entity.addPart("data[VendorReview][customer_id]",
							new StringBody(userid));
					entity.addPart("data[VendorReview][vendor_id]",
							new StringBody(chef_id));
					entity.addPart("data[VendorReview][review]",
							new StringBody(txt.getText().toString()));
					entity.addPart("data[VendorReview][ratings]",
							new StringBody(String.valueOf(ratevalue)));

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

				dialog.cancel();
				try {

					JSONObject obj = new JSONObject(s);
					JSONObject obj1 = obj.getJSONObject("data");
					String msg = obj1.getString("msg");

					if (msg.equals("success")) {
                         txt.setText("");
                         rateme.setRating((float) 0.0);
						getReview();
 
					} else {

						Toast.makeText(getApplicationContext(), msg, 5000)
								.show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		};
		if ((DetectNetwork.hasConnection(getApplicationContext())))
			updateTask.execute((Void[]) null);

	}

	// method for sending user detail on server
	protected void getReview() {
		// TODO Auto-generated method stub

		AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
			ProgressDialog dialog = new ProgressDialog(ChefProfile.this);

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

				rcustmerid.clear();
				rcustmername.clear();
				rcustmerimg.clear();
				rcustmerreview.clear();
				rcustmerrate.clear();
				rid.clear();
				rvid.clear();
				rtime.clear();
				Log.e("mmm", "msg");
				try {
					long milli = System.currentTimeMillis();
					String url = getResources().getString(R.string.url)
							+ "api/vendors/view/" + chef_id +".json?a="
							+ milli;

					MultipartEntity entity = new MultipartEntity(
							HttpMultipartMode.BROWSER_COMPATIBLE);

					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(url);

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
					JSONObject obj1 = obj.getJSONObject("data");
					JSONArray arr = obj1.getJSONArray("VendorReview");
					for (int i = 0; i < arr.length(); i++) {

						JSONObject obj2 = arr.getJSONObject(i);
						String id = obj2.getString("id");
						String customer_id = obj2.getString("customer_id");
						String vendor_id = obj2.getString("vendor_id");
						String review = obj2.getString("review");
						String ratings = obj2.getString("ratings");
						String time = obj2.getString("created");

						JSONObject obj3 = obj2.getJSONObject("Customer");

						String c_id = obj3.getString("id");
						String name = obj3.getString("name");
						String image = obj3.getString("image");

						rcustmerid.add(customer_id);
						rcustmername.add(name);
						rcustmerimg.add(image);
						rcustmerreview.add(review);
						rcustmerrate.add(ratings);
						rid.add(id);
						rvid.add(vendor_id);
						rtime.add(time);
						
						
					}

					adp=new Reviewadapter(getApplicationContext(), rid, rvid,
							rcustmerid, rcustmername, rcustmerimg, rcustmerreview,
							rcustmerrate, rtime);
					reviewlist.setAdapter(adp);
					adp.notifyDataSetChanged();
					
				} catch (Exception e) {

					e.printStackTrace();
				}
				dialog.cancel();
			}

		};
		if ((DetectNetwork.hasConnection(getApplicationContext())))
			updateTask.execute((Void[]) null);

	}

	public class Reviewadapter extends BaseAdapter {
		ArrayList<String> rcustmerid = new ArrayList<String>();
		ArrayList<String> rcustmername = new ArrayList<String>();
		ArrayList<String> rcustmerimg = new ArrayList<String>();
		ArrayList<String> rcustmerreview = new ArrayList<String>();
		ArrayList<String> rcustmerrate = new ArrayList<String>();
		ArrayList<String> rid = new ArrayList<String>();
		ArrayList<String> rvid = new ArrayList<String>();
		ArrayList<String> rtime = new ArrayList<String>();
		Context ctx;
		LayoutInflater inflater = null;
		ImageLoader il;

		public Reviewadapter(Context context, ArrayList<String> l1,
				ArrayList<String> l2, ArrayList<String> l3,
				ArrayList<String> l4, ArrayList<String> l5,
				ArrayList<String> l6, ArrayList<String> l7,ArrayList<String> l8) {
			ctx = context;
			rid = l1;
			rvid = l2;
			rcustmerid = l3;
			rcustmername = l4;
			rcustmerimg = l5;
			rcustmerreview=l6;
			rcustmerrate=l7;
			rtime=l8;
			il = new ImageLoader(ctx);
			inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return rcustmerreview.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		public class Holder {
			TextView chef_name;
			TextView chef_review;
			TextView time;
			TextView rate;
			ImageView user_img;
			RatingBar bar;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub

			Holder holder = new Holder();
			View rowView = arg1;
			
			rowView = inflater.inflate(R.layout.chef_profile_review_item, null);
			holder.chef_name = (TextView) rowView.findViewById(R.id.textView1);
			holder.chef_review = (TextView) rowView.findViewById(R.id.textView3);
			holder.time = (TextView) rowView.findViewById(R.id.textView4);
			holder.rate = (TextView) rowView.findViewById(R.id.textView2);
			holder.user_img = (ImageView) rowView.findViewById(R.id.imageView1);
			holder.bar = (RatingBar) rowView.findViewById(R.id.ratingBar1);

			holder.chef_name.setText(rcustmername.get(arg0));
			holder.chef_name.setTypeface(tf1);
			holder.chef_review.setText(rcustmerreview.get(arg0));
			holder.chef_review.setTypeface(tf3);
			holder.rate.setText("(" + rcustmerrate.get(arg0) + ")");
			holder.rate.setTypeface(tf1);
			holder.time.setText(rtime.get(arg0));
			holder.time.setTypeface(tf1);
			il.DisplayImage(rcustmerimg.get(arg0), holder.user_img);
			holder.bar.setRating(Float.parseFloat(rcustmerrate.get(arg0)));

			return rowView;
		}

	}

}
