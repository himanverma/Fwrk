package com.future.getfood;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
 
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref,pref1,pref2;
    Editor editor,editor1,editor2;
    Context _context;
 
    int PRIVATE_MODE = 0;
    int PRIVATE_MODE1 = 1;
    int PRIVATE_MODE2 = 2;
    private static final String PREF_NAME = "foodapp";
    private static final String PREF_NAME1 = "GCM";
    private static final String PREF_NAME2 = "userdetail";
    public static final String KEY_ID = "id";
    public static final String KEY_USER = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHOTO = "photo";
    public static final String KEY_PHONE = "phone";
    
    public static final String KEY_GCMID = "gcmid";
   
    
    public static final String KEY_FNAME = "fname";
    public static final String KEY_LNAME = "lname";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_AREA = "area";
    public static final String KEY_CITY = "city";
    public static final String KEY_ZIPCODE = "zipcode";
    public static final String KEY_PHONEMOB = "mobile";
    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        
        
        pref1 = _context.getSharedPreferences(PREF_NAME1, PRIVATE_MODE1);
        editor1 = pref1.edit();
        
        pref2 = _context.getSharedPreferences(PREF_NAME2, PRIVATE_MODE2);
        editor2 = pref2.edit();
        
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
   
    
    public void setUserDetail(String fanme,String lname,String add,String area,
    		String city,String zipcode,String ph){
        
        editor2.putString(KEY_FNAME, fanme);
        editor2.putString(KEY_LNAME, lname);
        editor2.putString(KEY_ADDRESS, add);
        editor2.putString(KEY_AREA, area);
        editor2.putString(KEY_CITY, city);
        editor2.putString(KEY_ZIPCODE, zipcode);
        editor2.putString(KEY_PHONEMOB, ph);
        editor2.commit();
    }   
    
    public void setGCMId(String gcmid){
        
        editor1.putString(KEY_GCMID, gcmid);
        editor1.commit();
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
     
    
    public HashMap<String, String> getUserAddress(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_FNAME, pref2.getString(KEY_FNAME, ""));
        user.put(KEY_LNAME, pref2.getString(KEY_LNAME, ""));
        user.put(KEY_ADDRESS, pref2.getString(KEY_ADDRESS, ""));
        user.put(KEY_AREA, pref2.getString(KEY_AREA, ""));
        user.put(KEY_CITY, pref2.getString(KEY_CITY, ""));
        user.put(KEY_ZIPCODE, pref2.getString(KEY_ZIPCODE, ""));
        user.put(KEY_PHONEMOB, pref2.getString(KEY_PHONEMOB, ""));
        return user;
    }
     
    
    public HashMap<String, String> getGCMID(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_GCMID, pref1.getString(KEY_GCMID, "0"));
        
        return user;
    }
}