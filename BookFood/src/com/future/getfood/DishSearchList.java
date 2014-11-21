package com.future.getfood;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.future.foodimg.DetectNetwork;
import com.future.listscroll.IInfiniteScrollListener;
import com.future.listscroll.InfiniteScrollListView;
import com.future.listscroll.InfiniteScrollOnScrollListener;

public class DishSearchList extends Activity {
	protected InfiniteScrollListView listView;
	private MyAdapter adapter;
	private InfiniteScrollOnScrollListener scrollListener;
	private ListTask listTask;
	private boolean executing = false;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.dish_search_list);

		listView = (InfiniteScrollListView) findViewById(R.id.list_view);
		search = (EditText) findViewById(R.id.editText1);
		// scrollListener = new InfiniteScrollOnScrollListener(this);
		Intent in = getIntent();
		search1 = in.getStringExtra("search");
		getdetail(search1);
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
			try{
				adapter.filter(text);
			}catch(Exception e){
				e.printStackTrace();
			}
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
	}

	// @Override
	// public void endIsNear() {
	// if (!executing) {
	//
	// // Toast.makeText(this, "End is near", Toast.LENGTH_SHORT).show();
	// // executing = true;
	// // listTask = new ListTask();
	// // listTask.execute(listView.getRealCount());
	// }
	// }
	//
	// // Item visibility code
	// @Override
	// public void onScrollCalled(int firstVisibleItem, int visibleItemCount,
	// int totalItemCount) {
	//
	// Log.e("fist", firstVisibleItem + " " + visibleItemCount + " "
	// + totalItemCount);
	// }

	private class ListTask extends AsyncTask<Integer, Void, ArrayList<String>> {

		@Override
		protected ArrayList<String> doInBackground(Integer... params) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ArrayList<String> items = new ArrayList<String>();
			// if (params[0] < total_num) {
			// for (int i = params[0]; i < (params[0] + 7); i++) {
			// String str = "Index: " + String.valueOf(i);
			// items.add(mylist.get(i));
			// }
			// }
			return items;
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			listView.appendItems(result);
			executing = false;

			if (result.size() > 0) {
				Toast.makeText(getApplicationContext(),
						"Loaded " + String.valueOf(result.size()) + " items",
						Toast.LENGTH_SHORT).show();
			} else {

				Toast.makeText(getApplicationContext(),
						"No more items to load", Toast.LENGTH_SHORT).show();
			}
		}
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
						}

						adapter = new MyAdapter(DishSearchList.this, mylist,
								namelist, pricelist, imglist, address_list,
								phone_list, mobile_list, chef_photo, chef_id,
								chef_rating,cid_list);
						listView.setAdapter(adapter);
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
}
