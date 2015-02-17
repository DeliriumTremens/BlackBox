package com.mx.hotbook.android.ui.view;

import java.security.MessageDigest;

import com.mx.hotbook.android.R;
import com.mx.hotbook.android.ui.AbstractUI;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Launcher extends AbstractUI {

  @Override
  public View getLayout(LayoutInflater inflater, ViewGroup container) {
	View view = inflater.inflate(R.layout.launcher, container, false);
	printDebugerHashKey();
	return view;
  }
	
  public void onBnLoginClick(View view){
	startActivity(new Intent(this, Login.class));
  }
  
  public void onBnRegisterClick(View view){
	startActivity(new Intent(this, Register.class));
  }
	
  private void printDebugerHashKey(){
	PackageInfo info;
	try {
		 info = getPackageManager().getPackageInfo("com.mx.hotbook.android", 
			                                 PackageManager.GET_SIGNATURES);
         for(Signature signature : info.signatures) {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(signature.toByteArray());
            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
         }
	} catch (Exception e) {
		e.printStackTrace();
	}
  }
}
