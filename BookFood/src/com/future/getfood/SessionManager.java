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
   
    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
     
    /**
     * Create login session
     * */
    public void setId(String userid){
       
        editor.putString(KEY_ID, userid);
        editor.commit();
    }   
   
    
    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_ID, pref.getString(KEY_ID, "0"));
      
        return user;
    }
     
   
}