package com.mx.hotbook.android.ui.core;

import java.util.Arrays;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.mx.hotbook.android.R;
import com.mx.hotbook.android.ui.AbstractUI;

@SuppressWarnings("deprecation")
public class Login extends AbstractUI{

  @Override
  public View getLayout(LayoutInflater inflater, ViewGroup container) {
	View view = inflater.inflate(R.layout.login, container, false);
	LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
	authButton.setOnErrorListener(new OnErrorListener() {   
	   @Override
	   public void onError(FacebookException error) {
	    Log.i(TAG, "Error " + error.getMessage());
	   }
	});
	authButton.setReadPermissions(Arrays.asList("public_profile","email"));
	authButton.setSessionStatusCallback(new Session.StatusCallback() {
	   @Override
	   public void call(Session session, SessionState state, Exception exception) {    
	     if(session.isOpened()) {
	       Log.i(TAG,"Access Token"+ session.getAccessToken());
	       Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
	            @Override
	            public void onCompleted(GraphUser user,Response response) {
	              if(user != null){ 
	                Log.i(TAG,"User ID "+ user.getId());
	                Log.i(TAG,"Email "+ user.asMap().get("email"));
	              }
	            }
	        });
	      }
	   }
	  });
	return view;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
  }
	 
}
