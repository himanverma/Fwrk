package com.future.getfood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public class PaymentProcess extends Activity{

	  WebView browser;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        //WebView Object
      
        browser=(WebView)findViewById(R.id.webkit);
        //Enable Javascript
        browser.getSettings().setJavaScriptEnabled(true);
        //Inject WebAppInterface methods into Web page by having Interface 'Android' 
        browser.addJavascriptInterface(new WebAppInterface(this), "Android");
        browser.loadUrl("http://apps.programmerguru.com/examples/androidjs.html");
    }
    //Class to be injected in Web page
    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }
        
        /**
         * Show Toast Message
         * @param toast
         */
        @JavascriptInterface
        public void showToast(String toast) {
           
            Intent in=new Intent(PaymentProcess.this,DishesActivity.class);
            startActivity(in);
            finish();
        }
        
       
    }
}
