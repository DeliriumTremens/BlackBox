package com.mx.hotbook.android.ui.core;

import java.security.MessageDigest;

import com.mx.hotbook.android.R;
import com.mx.hotbook.android.ui.AbstractUI;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

public class Launcher extends AbstractUI {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher);
		final Activity caller = this;
		printDebugerHashKey();
		Thread splashTread = new Thread() {
		      public void run() {
		        try {
			         sleep(com.mx.hotbook.android.constant.Config.SPLASH_PERIOD);
				     startActivity(new Intent(caller.getApplicationContext(), Login.class)); 
			    } catch(Exception e) {
				  Log.e(TAG, "Inicialization failed: " + e);
			    } 
		      } 
		    };
			splashTread.start();
	}
	
	private void printDebugerHashKey(){
		PackageInfo info;
		try {
			info = getPackageManager().getPackageInfo(
			        "com.mx.hotbook.android", 
			        PackageManager.GET_SIGNATURES);
		
        for (Signature signature : info.signatures) {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(signature.toByteArray());
            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
