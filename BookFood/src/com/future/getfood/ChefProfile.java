package com.future.getfood;

import android.app.Activity;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class ChefProfile extends Activity{

	ImageView back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.chef_profile);
		
		back=(ImageView)findViewById(R.id.imageView1);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent in=new Intent(ChefProfile.this,DishesActivity.class);
//				startActivity(in);
				finish();
			}
		});
		
		
	}
	
	@Override
	public void onBackPressed() {
//		Intent in=new Intent(ChefProfile.this,DishesActivity.class);
//		startActivity(in);
		finish();
	}
}
