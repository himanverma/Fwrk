package com.sarvajit.database;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText name, address, age;
	Button submit;
	private TestAdapter testAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		testAdapter = new TestAdapter(MainActivity.this);
//		name = (EditText) findViewById(R.id.editText1);
//		address = (EditText) findViewById(R.id.editText2);
//		age = (EditText) findViewById(R.id.editText3);
//		submit = (Button) findViewById(R.id.button1);
//		
//		submit.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				try {
//					testAdapter.open();
//					testAdapter.vname = name.getText().toString();
//					testAdapter.vaddress = address.getText().toString();
//					testAdapter.vage = Integer.parseInt(age.getText().toString());
//					testAdapter.createGroup();
//					testAdapter.close();
//					name.setText("");
//					address.setText("");
//					age.setText("");
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//			}
//		});
	}
}
