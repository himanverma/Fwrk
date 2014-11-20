package com.future.getfood;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
 
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    Editor editor;
    Context _context;
 
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "foodapp";
    public static final String KEY_ID = "id";
    public static final String KEY_USER = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHOTO = "photo";
    public static final String KEY_PHONE = "phone";
   
    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
     
    /**
     * Create login session
     * */
    public void setId(String userid,String user,String eml,String photo,String ph){
       
        editor.putString(KEY_ID, userid);
        editor.putString(KEY_USER, user);
        editor.putString(KEY_EMAIL, eml);
        editor.putString(KEY_PHOTO, photo);
        editor.putString(KEY_PHONE, ph);
        editor.commit();
    }   
   
    
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_ID, pref.getString(KEY_ID, "0"));
        user.put(KEY_USER, pref.getString(KEY_USER, "0"));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, "0"));
        user.put(KEY_PHOTO, pref.getString(KEY_PHOTO, "0"));
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, "0"));
        return user;
    }
     
   
}