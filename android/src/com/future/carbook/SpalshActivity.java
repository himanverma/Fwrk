package com.future.carbook;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SpalshActivity extends ActionBarActivity {

	 private static int SPLASH_TIME_OUT = 3000;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		
		 new Handler().postDelayed(new Runnable() {
			 
	            /* 20/10/14
	             * Showing splash screen with a timer. This will be useful when you
	             * want to show case your app logo / company
	             */
	 
	            @Override
	            public void run() {
	               
	                Intent i = new Intent(SpalshActivity.this, CarbyNear.class);
	                startActivity(i);
	                finish();
	            }
	        }, SPLASH_TIME_OUT);
		 
	}

}
