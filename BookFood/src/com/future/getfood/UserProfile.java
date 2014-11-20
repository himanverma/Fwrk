package com.future.getfood;

import java.util.HashMap;

import com.future.foodimg.ImageLoader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfile extends Activity {

	TextView username;
	TextView useremail;
	TextView usermob;
	ImageView img;
	SessionManager sess;
	String userid,nuser,mobuser,euser,imguser;
	ImageLoader il;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.user_profile);

		sess=new SessionManager(this);
		HashMap<String, String>map=sess.getUserDetails();
		userid=map.get(SessionManager.KEY_ID);
		nuser=map.get(SessionManager.KEY_USER);
		mobuser=map.get(SessionManager.KEY_PHONE);
		euser=map.get(SessionManager.KEY_EMAIL);
		imguser=map.get(SessionManager.KEY_PHOTO);
		
		
		username=(TextView)findViewById(R.id.textView2);
		useremail=(TextView)findViewById(R.id.textView3);
		usermob=(TextView)findViewById(R.id.textView4);
		img=(ImageView)findViewById(R.id.imageView2);
		
		il=new ImageLoader(this);
		username.setText(nuser);
		
		if(euser.equals("0")){
			useremail.setVisibility(View.GONE);
		}else{
			
			useremail.setVisibility(View.VISIBLE);
			useremail.setText(euser);
		}
		
		if(mobuser.equals("0")){
			usermob.setVisibility(View.GONE);
		}else{
			
			usermob.setVisibility(View.VISIBLE);
			usermob.setText(mobuser);
		}
		
		il.DisplayImage(imguser, img);
		
		((ImageView) findViewById(R.id.imageView1))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});
	}
}
