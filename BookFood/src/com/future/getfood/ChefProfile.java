package com.future.getfood;

import com.future.foodimg.ImageLoader;

import android.app.Activity;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class ChefProfile extends Activity {

	ImageView back;
	String img_url;
	String chef_name;
	String chef_add;
	String chef_mob;
	String chef_pho;
	ImageView chef_photo;
	TextView chefname;
	TextView chefadd;
	TextView chefmob;
	ImageLoader il;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.chef_profile);

		il = new ImageLoader(this);
		Intent in = getIntent();
		img_url = in.getStringExtra("photo");
		chef_name = in.getStringExtra("name");
		chef_add = in.getStringExtra("address");
		chef_mob = in.getStringExtra("mob");
		chef_pho = in.getStringExtra("phone");

		chef_photo = (ImageView) findViewById(R.id.imageView2);
		chefname = (TextView) findViewById(R.id.textView2);
		chefadd = (TextView) findViewById(R.id.textView3);
		chefmob = (TextView) findViewById(R.id.textView4);

		// set values of chef

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

		if (chef_mob.equals(null)||chef_pho.equals(null)) {

			chefmob.setText("Not set");
			
		} else {
			
		if(chef_mob.equals(null)){
			
			chefmob.setText(chef_pho);
		}
		else if(chef_pho.equals(null)){
			
			chefmob.setText(chef_mob);
		}else{
			
			chefmob.setText(chef_pho+" / "+chef_mob);
		}
			
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
		// Intent in=new Intent(ChefProfile.this,DishesActivity.class);
		// startActivity(in);
		finish();
	}
}
