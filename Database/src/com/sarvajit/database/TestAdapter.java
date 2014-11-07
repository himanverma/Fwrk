package com.sarvajit.database;

import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class TestAdapter {
	
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_ADDRESS = "address";
	public static final String KEY_AGE = "age";
	
	public static int vid;
	public static String vname;
	public static String vaddress;
	public static int vage;
	
	private static final String DATABASE_TABLE = "test_TABLE";
	private Context context;
	private SQLiteDatabase db;
	private DBHelper dbHelper;
	
	public TestAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
	}
	
	public TestAdapter open() throws SQLException{
		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();
		db = dbHelper.getReadableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}
	
	private ContentValues createContentValues() {
		ContentValues contentValues = new ContentValues();
		contentValues.put(KEY_NAME, vname);
		contentValues.put(KEY_ADDRESS, vaddress);
		contentValues.put(KEY_AGE, vage);
		return contentValues;
		
	}
	
	public long createGroup() {
		ContentValues initialValue = createContentValues();
		return db.insert(DATABASE_TABLE, null, initialValue);
		
	}
	
	
}
