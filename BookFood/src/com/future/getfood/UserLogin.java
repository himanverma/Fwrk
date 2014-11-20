package com.future.getfood;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.future.foodimg.DetectNetwork;
import com.google.android.gms.plus.model.people.Person.PlacesLived;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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
	TextView t1,t2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.order_confirmation);
		
		tf1 = Typeface.createFromAsset(getAssets(), "Roboto-Bold.ttf");
		
		t1=(TextView) findViewById(R.id.textView1);
		t2=(TextView) findViewById(R.id.textView3);
		
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

		((ImageView) findViewById(R.id.imageView1))
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
				Intent in = new Intent(UserLogin.this,
						FacebookLogin.class);
				startActivity(in);
			}
		});

		placeorder.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String txt = pho_num.getText().toString();

				if (txt.length() > 9) {

					Intent in=new Intent(UserLogin.this,Registration.class);
					in.putExtra("dish",dishname);
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
