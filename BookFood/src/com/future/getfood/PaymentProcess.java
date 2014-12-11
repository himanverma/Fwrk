package com.future.getfood;

import com.future.getfood.AnalyticsSampleApp.TrackerName;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

public class PaymentProcess extends Activity {

	private WebView myWebView;
	String url;
	RelativeLayout rel;
	Button btn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.payment);

		Intent in = getIntent();
		url = in.getStringExtra("url");

		rel = (RelativeLayout) findViewById(R.id.nnnn);
		// WebView Object
		myWebView = (WebView) findViewById(R.id.webkit);

		btn = (Button) findViewById(R.id.button1);

		
		 Tracker t = ((AnalyticsSampleApp) getApplicationContext()).getTracker(
	               TrackerName.APP_TRACKER);
	       t.setScreenName("Payment Process Make payment");
	       t.send(new HitBuilders.AppViewBuilder().build());
	       
		if (url.equals("cod")) {
			rel.setVisibility(View.VISIBLE);
			myWebView.setVisibility(View.GONE);
			btn.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent in = new Intent(PaymentProcess.this,
							DishHome.class);
					startActivity(in);
					finish();
				}
			});

		} else {
			rel.setVisibility(View.GONE);
			myWebView.setVisibility(View.VISIBLE);
			myWebView.getSettings().setJavaScriptEnabled(true);
			myWebView.setWebViewClient(new MyWebViewClient());
			myWebView.addJavascriptInterface(new JavaScriptInterface(
					PaymentProcess.this), "Android");
			myWebView.loadUrl(url);
		}
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
	

	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (Uri.parse(url).getHost().equals("demo.mysamplecode.com")) {
				return false;
			}
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
			startActivity(intent);
			return true;
		}
	}

	public class JavaScriptInterface {
		Context mContext;

		// Instantiate the interface and set the context
		JavaScriptInterface(Context c) {
			mContext = c;
		}

		@JavascriptInterface
		public void welcome(String toast) {

			Intent in = new Intent(PaymentProcess.this, DishHome.class);
			startActivity(in);
			finish();
		}
	}
}
