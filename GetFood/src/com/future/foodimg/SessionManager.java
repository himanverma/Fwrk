package com.future.foodimg;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {

	SharedPreferences pref;
	Editor editor;
	Context _context;
	int PRIVATE_MODE = 0;

	public static final String KEY_USERNAME = "username";
	public static final String KEY_EMAIL = "email";
	public static final String KEY_MOBLIE = "mobile";
	public static final String KEY_USERPHOTO = "userphoto";
	public static final String KEY_IDPROOF = "idproof";
	public static final String KEY_ADDRESS = "address";
	public static final String KEY_CITY = "city";
	public static final String KEY_STATE = "state";
	public static final String KEY_COUNTRY = "country";
	public static final String KEY_TYPE = "type";
	public static final String KEY_TOKEN = "token";
	public static final String KEY_LAT = "lat";
	public static final String KEY_LNG = "lng";

	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences("UserDetail", PRIVATE_MODE);
		editor = pref.edit();
	}

	/**
	 * Create login session
	 * */
	public void createLoginSession(String username, String email,
			String mobile, String photo, String idproof, String address,
			String city, String state, String country, String type,
			String token, String lat, String lng) {

		editor.putString(KEY_USERNAME, username);
		editor.putString(KEY_EMAIL, email);
		editor.putString(KEY_MOBLIE, mobile);
		editor.putString(KEY_USERPHOTO, photo);
		editor.putString(KEY_IDPROOF, idproof);
		editor.putString(KEY_ADDRESS, address);
		editor.putString(KEY_CITY, city);
		editor.putString(KEY_STATE, state);
		editor.putString(KEY_COUNTRY, country);
		editor.putString(KEY_TYPE, type);
		editor.putString(KEY_TOKEN, token);
		editor.putString(KEY_LAT, lat);
		editor.putString(KEY_LNG, lng);

		editor.commit();
	}

	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();

		user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
		user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
		user.put(KEY_MOBLIE, pref.getString(KEY_MOBLIE, null));
		user.put(KEY_USERPHOTO, pref.getString(KEY_USERPHOTO, null));
		user.put(KEY_IDPROOF, pref.getString(KEY_IDPROOF, null));
		user.put(KEY_ADDRESS, pref.getString(KEY_ADDRESS, null));
		user.put(KEY_CITY, pref.getString(KEY_CITY, null));
		user.put(KEY_STATE, pref.getString(KEY_STATE, null));
		user.put(KEY_COUNTRY, pref.getString(KEY_COUNTRY, null));
		user.put(KEY_TYPE, pref.getString(KEY_TYPE, null));
		user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));
		user.put(KEY_LAT, pref.getString(KEY_LAT, null));
		user.put(KEY_LNG, pref.getString(KEY_LNG, null));

		// return user
		return user;
	}

}
