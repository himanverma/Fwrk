package com.future.getfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NextAct extends Activity{

	TextView tv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.next);
		
		Intent in=getIntent();
		tv=(TextView) findViewById(R.id.textView1);
		
		tv.setText(in.getStringExtra("text"));
	}
}
