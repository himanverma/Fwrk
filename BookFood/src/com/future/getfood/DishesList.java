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

import com.future.foodimg.DetectNetwork;
import com.future.foodimg.ImageLoader;
import com.future.getfood.AnalyticsSampleApp.TrackerName;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DishesList extends Activity {

	GridView grid;
	ImageView back;
	protected HttpResponse response;
	protected String s;
	ArrayList<String> dishname = new ArrayList<String>();
	ArrayList<String> dishimg = new ArrayList<String>();
	CustomGrid adapter;
	SessionManager sess;
	String userid;
	EditText search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.dishlist);

		Tracker t = ((AnalyticsSampleApp) DishesList.this.getApplication()).getTracker(
                TrackerName.APP_TRACKER);
        t.setScreenName("DishesList for filter dishes Check");
        t.send(new HitBuilders.AppViewBuilder().build());

		sess = new SessionManager(this);
		HashMap<String, String> map = sess.getUserDetails();
		userid = map.get(SessionManager.KEY_ID);
		
		search = (EditText) findViewById(R.id.editText1);

		back = (ImageView) findViewById(R.id.imageView1);

		grid = (GridView) findViewById(R.id.grid);

		getdish();

		((RelativeLayout)findViewById(R.id.nn2)).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (userid.equals("0")) {
					Toast.makeText(getApplicationContext(),
							"Make your profile first.", 5000).show();
				} else {
					Tracker t = ((AnalyticsSampleApp) DishesList.this.getApplication()).getTracker(
	                        TrackerName.APP_TRACKER);
					 // Build and send an Event.
					t.send(new HitBuilders.EventBuilder()
					    .setCategory("User Profile")  // category i.e. Player Buttons
					    .setAction("Check own profile")    // action i.e.  Play
					    .setLabel("clicked")    // label i.e.  any meta-data
					    .build());
					
					Intent in = new Intent(DishesList.this,
							UserProfile.class);
					startActivity(in);
				}
			}
		});
		grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Toast.makeText(getApplication(),
				// dishname.get(position),5000).show();

				Tracker t = ((AnalyticsSampleApp) DishesList.this.getApplication()).getTracker(
                        TrackerName.APP_TRACKER);
                t.setScreenName("DishesList search dishes");
                t.send(new HitBuilders.AppViewBuilder().build());
                
                // Build and send an Event.
				t.send(new HitBuilders.EventBuilder()
				    .setCategory("Chef dishes")  // category i.e. Player Buttons
				    .setAction("search dishes")    // action i.e.  Play
				    .setLabel("clicked")    // label i.e.  any meta-data
				    .build());
				
                
				Intent in = new Intent(DishesList.this, DishSearchList.class);
				in.putExtra("search", dishname.get(position));
				startActivity(in);
			}
		});

		((RelativeLayout) findViewById(R.id.nn1))
		.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent in = new Intent(DishesList.this, DishesActivity.class);
//				startActivity(in);
				finish();
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
				try{
				adapter.filter(text);
				}catch(Exception e){
					e.printStackTrace();
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
//		userid = map.get(SessionManager.KEY_ID);
//	}
	
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
	protected void getdish() {
		// TODO Auto-generated method stub

		AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
			ProgressDialog dialog = new ProgressDialog(DishesList.this);

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
							+ "api/recipes.json?a=" + milli;

					MultipartEntity entity = new MultipartEntity(
							HttpMultipartMode.BROWSER_COMPATIBLE);

					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost(url);

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

					JSONObject obj = new JSONObject(s);
					JSONArray arr = obj.getJSONArray("data");
					for (int i = 0; i < arr.length(); i++) {

						JSONObject obj1 = arr.getJSONObject(i);
						JSONObject obj2 = obj1.getJSONObject("Recipe");
						String dish_name = obj2.getString("recipe_name");
						String dish_img = obj2.getString("image");

						dishname.add(dish_name);
						dishimg.add(dish_img);

						// Log.e("hhhh", dish_name);

					}
					
				} catch (Exception e) {

					e.printStackTrace();
				}
				
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// what to do when background task is completed
				adapter = new CustomGrid(DishesList.this, dishname, dishimg);
				grid.setAdapter(adapter);// (new CustomGrid(DishesList.this,
											// dishname,dishimg));
				// adapter.notifyDataSetChanged();
				
				dialog.cancel();
			}

		};
		if ((DetectNetwork.hasConnection(getApplicationContext())))
			updateTask.execute((Void[]) null);

	}

}
