package com.future.getfood;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;

import com.future.getfood.AnalyticsSampleApp.TrackerName;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.service.textservice.SpellCheckerService.Session;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserLogin extends Activity {

	private Button fb;
	protected HttpResponse response;
	protected String s;
	EditText pho_num;
	Button placeorder;
	SessionManager sess;
	String dishname;
	String chkname;
	String price;
	private Typeface tf1;
	TextView t1, t2;
	private String userid, cid, chkk;
	String fname, lname, address, area, city, zipcode, mobile,chk_value;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.order_confirmation);

		sess = new SessionManager(this);
		HashMap<String, String> map = sess.getUserDetails();
		userid = map.get(SessionManager.KEY_ID);

		tf1 = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");

		t1 = (TextView) findViewById(R.id.textView1);
		t2 = (TextView) findViewById(R.id.textView3);

		t1.setTypeface(tf1);
		t2.setTypeface(tf1);

		// getting value from orderDishes
		Intent in = getIntent();
		dishname = in.getStringExtra("dish");
		chkname = in.getStringExtra("chk");
		price = in.getStringExtra("price");
		cid = in.getStringExtra("cid");

		fname = in.getStringExtra("fname");
		lname = in.getStringExtra("lname");
		address = in.getStringExtra("address");
		area = in.getStringExtra("area");

		city = in.getStringExtra("mcity");
		zipcode = in.getStringExtra("zipcode");
		mobile = in.getStringExtra("phone_num");
		chk_value = in.getStringExtra("chk_value");

		sess = new SessionManager(this);
		pho_num = (EditText) findViewById(R.id.editText1);
		placeorder = (Button) findViewById(R.id.imageView4);

		// google analytics
		Tracker t = ((AnalyticsSampleApp) UserLogin.this.getApplication())
				.getTracker(TrackerName.APP_TRACKER);
		t.setScreenName("UserLogin");
		t.send(new HitBuilders.AppViewBuilder().build());

		((RelativeLayout) findViewById(R.id.bbb))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (userid.equals("0")) {
							Toast.makeText(getApplicationContext(),
									"Make your profile first.", 5000).show();
						} else {

							Tracker t = ((AnalyticsSampleApp) UserLogin.this
									.getApplication())
									.getTracker(TrackerName.APP_TRACKER);
							// Build and send an Event.
							t.send(new HitBuilders.EventBuilder()
									.setCategory("User Login") // category i.e.
																// Player
																// Buttons
									.setAction("Check own Profile") // action
																	// i.e. Play
									.setLabel("clicked") // label i.e. any
															// meta-data
									.build());

							Intent in = new Intent(UserLogin.this,
									UserProfile.class);
							startActivity(in);
						}
					}
				});
		((RelativeLayout) findViewById(R.id.bbbb))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						// Intent in = new Intent(UserLogin.this,
						// OrderDishes.class);
						// startActivity(in);
						finish();
					}
				});

		fb = (Button) findViewById(R.id.imageView3);

		fb.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Tracker t = ((AnalyticsSampleApp) UserLogin.this
						.getApplication()).getTracker(TrackerName.APP_TRACKER);
				// Build and send an Event.
				t.send(new HitBuilders.EventBuilder()
						.setCategory("User Facebook Login") // category i.e.
															// Player Buttons
						.setAction("Facebook Login") // action i.e. Play
						.setLabel("clicked") // label i.e. any meta-data
						.build());

				Intent in = new Intent(UserLogin.this, FacebookLogin.class);

				in.putExtra("dish", dishname);
				in.putExtra("chk", chkname);
				in.putExtra("price", price);
				in.putExtra("cid", cid);
				in.putExtra("fname", fname);
				in.putExtra("lname", lname);
				in.putExtra("address", address);
				in.putExtra("area", area);
				in.putExtra("mcity", city);
				in.putExtra("zipcode", zipcode);
				in.putExtra("phone_num", mobile);
				in.putExtra("chk_value", chk_value);
				startActivity(in);

				finish();

			}
		});

		placeorder.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String txt = pho_num.getText().toString();

				if (txt.length() > 9) {

					Tracker t = ((AnalyticsSampleApp) UserLogin.this
							.getApplication())
							.getTracker(TrackerName.APP_TRACKER);
					// Build and send an Event.
					t.send(new HitBuilders.EventBuilder()
							.setCategory("User Login") // category i.e. Player
														// Buttons
							.setAction("Make mobile number Registration") // action
																			// i.e.
																			// Play
							.setLabel("clicked") // label i.e. any meta-data
							.build());

					Intent in = new Intent(UserLogin.this, Registration.class);
					in.putExtra("dish", dishname);
					in.putExtra("chk", chkname);
					in.putExtra("price", price);
					in.putExtra("phone", pho_num.getText().toString());
					in.putExtra("cid", cid);
					in.putExtra("fname", fname);
					in.putExtra("lname", lname);
					in.putExtra("address", address);
					in.putExtra("area", area);
					in.putExtra("mcity", city);
					in.putExtra("zipcode", zipcode);
					in.putExtra("phone_num", mobile);
					in.putExtra("chk_value", chk_value);
					startActivity(in);
					finish();

				} else {

					pho_num.setError("Please fill correct phone number!");
				}
			}
		});
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

}
