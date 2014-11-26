package com.future.getfood;

import java.util.HashMap;
import org.apache.http.HttpResponse;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserLogin extends Activity {

	ImageView fb;
	protected HttpResponse response;
	protected String s;
	EditText pho_num;
	ImageView placeorder;
	SessionManager sess;
	String dishname;
	String chkname;
	String price;
	private Typeface tf1;
	TextView t1, t2;
	private String userid;
    
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
		// getting value from MyAdapter
		Intent in = getIntent();
		dishname = in.getStringExtra("dish");
		chkname = in.getStringExtra("chk");
		price = in.getStringExtra("price");

		sess = new SessionManager(this);
		pho_num = (EditText) findViewById(R.id.editText1);
		placeorder = (ImageView) findViewById(R.id.imageView4);

		((RelativeLayout) findViewById(R.id.bbb))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (userid.equals("0")) {
							Toast.makeText(getApplicationContext(),
									"Make your profile first.", 5000).show();
						} else {

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
						finish();
					}
				});

		fb = (ImageView) findViewById(R.id.imageView3);
		fb.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(UserLogin.this, FacebookLogin.class);
				startActivity(in);
			}
		});

		placeorder.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String txt = pho_num.getText().toString();

				if (txt.length() > 9) {

					Intent in = new Intent(UserLogin.this, Registration.class);
					in.putExtra("dish", dishname);
					in.putExtra("chk", chkname);
					in.putExtra("price", price);
					in.putExtra("phone", pho_num.getText().toString());
					startActivity(in);
					finish();

				} else {

					pho_num.setError("Please fill correct phone number!");
				}
			}
		});
	}

}
