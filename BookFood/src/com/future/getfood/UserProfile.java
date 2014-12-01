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

import com.future.foodimg.CircularImageView;
import com.future.foodimg.DetectNetwork;
import com.future.foodimg.ImageLoader;
import com.future.getfood.ChefProfile.Reviewadapter;
import com.future.getfood.ChefProfile.Reviewadapter.Holder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile extends Activity {

	TextView username;
	TextView useremail;
	TextView usermob;
	// ImageView img;
	SessionManager sess;
	String userid, nuser, mobuser, euser, imguser;
	ImageLoader il;
	protected HttpResponse response;
	Orderadapter adp;
	private Typeface tf1, tf2, tf3, tf4;
	protected String s;

	ArrayList<String> dish_list = new ArrayList<String>();
	ArrayList<String> vender_name = new ArrayList<String>();
	ArrayList<String> date_list = new ArrayList<String>();
	ArrayList<String> address_list = new ArrayList<String>();
	ArrayList<String> dish_price = new ArrayList<String>();
	ArrayList<String> dish_img = new ArrayList<String>();
	ListView lv;
	CircularImageView circularImageView;
	TextView nodata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.user_profile);

		tf1 = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");
		tf2 = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
		tf3 = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
		tf4 = Typeface.createFromAsset(getAssets(), "Roboto-Thin.ttf");

		sess = new SessionManager(this);
		HashMap<String, String> map = sess.getUserDetails();
		userid = map.get(SessionManager.KEY_ID);
		nuser = map.get(SessionManager.KEY_USER);
		mobuser = map.get(SessionManager.KEY_PHONE);
		euser = map.get(SessionManager.KEY_EMAIL);
		imguser = map.get(SessionManager.KEY_PHOTO);

		username = (TextView) findViewById(R.id.textView2);
		useremail = (TextView) findViewById(R.id.textView3);
		usermob = (TextView) findViewById(R.id.textView4);
		// img=(ImageView)findViewById(R.id.imageView2);

		circularImageView = (CircularImageView) findViewById(R.id.imageView2);
		circularImageView.setBorderColor(Color.GRAY);
		circularImageView.setBorderWidth(5);

		lv = (ListView) findViewById(R.id.listView1);
		nodata = (TextView) findViewById(R.id.textView6);
		nodata.setTypeface(tf1);
		nodata.setText("No any order made by " + nuser);
		il = new ImageLoader(this);
		username.setText(nuser);
		username.setTypeface(tf3);

		if (euser.equals("0")) {
			useremail.setVisibility(View.GONE);
		} else {

			useremail.setVisibility(View.VISIBLE);
			useremail.setText(euser);
			useremail.setTypeface(tf3);
		}

		if (mobuser.equals("0")) {
			usermob.setVisibility(View.GONE);
		} else {

			usermob.setVisibility(View.VISIBLE);
			usermob.setText(mobuser);
			usermob.setTypeface(tf3);
		}

			il.DisplayImage1(imguser, circularImageView);

		
		orderHistory();

		((TextView) findViewById(R.id.textView7))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						Intent in = new Intent(UserProfile.this,
								UpdateUserProfile.class);
						startActivity(in);
						finish();
					}
				});
		((RelativeLayout) findViewById(R.id.jkl))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});
	}

	// method for sending user detail on server
	protected void orderHistory() {
		// TODO Auto-generated method stub

		AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
			ProgressDialog dialog = new ProgressDialog(UserProfile.this);

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
				dish_list.clear();
				vender_name.clear();
				date_list.clear();
				address_list.clear();
				dish_price.clear();
				dish_img.clear();

				try {
					long milli = System.currentTimeMillis();
					String url = getResources().getString(R.string.url)
							+ "api/orders/order/" + userid + ".json?a=" + milli;

					MultipartEntity entity = new MultipartEntity(
							HttpMultipartMode.BROWSER_COMPATIBLE);

					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(url);

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
					JSONArray arr = obj.getJSONArray("data");
					if (arr.length() <= 0) {

						nodata.setVisibility(View.VISIBLE);
						lv.setVisibility(View.GONE);

					} else {
						for (int i = 0; i < arr.length(); i++) {

							JSONObject obj1 = arr.getJSONObject(i);
							JSONObject obj2 = obj1.getJSONObject("Order");
							String recipe_names = obj2
									.getString("recipe_names");

							JSONObject obj3 = obj1.getJSONObject("Combination");
							String date = obj3.getString("date");
							String price = obj3.getString("price");
							String dishimg = obj3.getString("image");

							JSONObject obj4 = obj3.getJSONObject("Vendor");
							String vendername = obj4.getString("name");

							JSONObject obj5 = obj1.getJSONObject("Address");
							String f_name = obj5.getString("f_name");
							String l_name = obj5.getString("l_name");
							String address = obj5.getString("address");
							String area = obj5.getString("area");
							String city = obj5.getString("city");
							String zipcode = obj5.getString("zipcode");
							String phone_number = obj5
									.getString("phone_number");

							dish_list.add(recipe_names);
							vender_name.add(vendername);
							date_list.add(date);
							address_list.add(address + "," + area + "," + city
									+ "," + zipcode);
							dish_price.add(price);
							dish_img.add(dishimg);

						}

						adp = new Orderadapter(getApplicationContext(),
								dish_list, vender_name, date_list,
								address_list, dish_price, dish_img);
						lv.setAdapter(adp);
						nodata.setVisibility(View.GONE);
						lv.setVisibility(View.VISIBLE);
						adp.notifyDataSetChanged();
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

	public class Orderadapter extends BaseAdapter {
		ArrayList<String> dish_list = new ArrayList<String>();
		ArrayList<String> vender_name = new ArrayList<String>();
		ArrayList<String> date_list = new ArrayList<String>();
		ArrayList<String> address_list = new ArrayList<String>();
		ArrayList<String> dish_price = new ArrayList<String>();
		ArrayList<String> dish_img = new ArrayList<String>();
		Context ctx;
		LayoutInflater inflater = null;
		ImageLoader il;

		public Orderadapter(Context context, ArrayList<String> l1,
				ArrayList<String> l2, ArrayList<String> l3,
				ArrayList<String> l4, ArrayList<String> l5, ArrayList<String> l6) {
			ctx = context;
			dish_list = l1;
			vender_name = l2;
			date_list = l3;
			address_list = l4;
			dish_price = l5;
			dish_img = l6;

			il = new ImageLoader(ctx);
			inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dish_list.size();
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
			TextView vender_name;
			TextView time;
			TextView price;
			TextView address;
			ImageView dish_img;

		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub

			Holder holder = new Holder();
			View rowView = arg1;

			rowView = inflater.inflate(R.layout.user_profile_item, null);
			holder.dish_name = (TextView) rowView.findViewById(R.id.dishname);
			holder.vender_name = (TextView) rowView
					.findViewById(R.id.textView1);
			holder.time = (TextView) rowView.findViewById(R.id.textView2);
			holder.price = (TextView) rowView.findViewById(R.id.textView4);
			holder.address = (TextView) rowView.findViewById(R.id.textView3);
			holder.dish_img = (ImageView) rowView.findViewById(R.id.imageView1);

			holder.dish_name.setText(dish_list.get(arg0));
			holder.dish_name.setTypeface(tf1);
			holder.vender_name.setText("By " + vender_name.get(arg0));
			holder.vender_name.setTypeface(tf3);
			String[] dd = date_list.get(arg0).split(" ");
			// holder.time.setText(date_list.get(arg0));
			holder.time.setText("Order date: " + dd[0]);
			holder.time.setTypeface(tf1);
			holder.price.setText("Price: Rs" + dish_price.get(arg0));
			holder.price.setTypeface(tf1);
			holder.address.setText(address_list.get(arg0));
			holder.address.setTypeface(tf2);

			il.DisplayImage(dish_img.get(arg0), holder.dish_img);

			return rowView;
		}

	}

}
