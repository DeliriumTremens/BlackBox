package com.mx.hotbook.android.ui.core;

import java.security.MessageDigest;

import com.mx.hotbook.android.R;
import com.mx.hotbook.android.ui.AbstractUI;

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
		printDebugerHashKey();
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
