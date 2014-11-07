package com.sarvajit.database;

/* app test*/
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "TestDB";
	private static final int DATABASE_VERSION = 2;
	
	private static final String CREATE_TEST = "create table test_TABLE(_id integer primary key autoincrement,"
			+ "name text,"
			+ "address text,"
			+ "age int);";
	
	//what did here

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL("DROP TABLE IF EXISTS test_TABLE");
		database.execSQL(CREATE_TEST);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		onCreate(database);
	}

}
