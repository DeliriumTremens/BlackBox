package com.mx.hotbook.android.ui.view;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.FacebookException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.OnErrorListener;
import com.loopj.android.http.RequestParams;
import com.mx.hotbook.android.R;
import com.mx.hotbook.android.constant.Config;
import com.mx.hotbook.android.ui.AbstractUI;
import com.mx.hotbook.android.ui.util.ErrorManager;
import com.mx.hotbook.android.util.ws.RestClient;
import com.mx.hotbook.android.util.ws.RestResponseHandler;

import org.json.*;

public class Register extends AbstractUI implements Session.StatusCallback
                                                       , OnErrorListener {
	
  private EditText etMail = null;
  private EditText etUserName = null;
  private EditText etPassword = null;
  private EditText etRepeatPassword = null;
  private EditText etName = null;
  private EditText etWebPage = null;
  private EditText etBiography = null;
  private ImageView ivProfile = null;
	
  @Override
  public View getLayout(LayoutInflater inflater, ViewGroup container) {
	View view = inflater.inflate(R.layout.register, container, false);
	LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
	authButton.setOnErrorListener(this);
	authButton.setReadPermissions(Config.FB_PERMISSIONS);
	authButton.setSessionStatusCallback(this);
	etMail = (EditText) view.findViewById(R.id.etMail);
	etUserName = (EditText) view.findViewById(R.id.etUserName);
	etPassword = (EditText) view.findViewById(R.id.etPassword);
	etRepeatPassword = (EditText) view.findViewById(R.id.etRepeatPassword);
	etName = (EditText) view.findViewById(R.id.etName);
	etWebPage = (EditText) view.findViewById(R.id.etWebPage);
	etBiography = (EditText) view.findViewById(R.id.etBiography);
	ivProfile = (ImageView) view.findViewById(R.id.ivProfile);
	return view;
  }
  
  public void onClickBnRegister(View view){
	  RequestParams params = new RequestParams();
	  params.put("username", "Test1");
	  params.put("password", "Test1");
	  params.put("password", "Test1");
	  params.put("nombre", "Test1");
	  params.put("mail", "Test1");
	  RestClient.post("sign_up", params, new RestResponseHandler(this) {
		 @Override
	     public void onSuccess(JSONObject response) throws JSONException {
	     }
      });
  }
  
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
  }
  
  @Override
  public void call(Session session, SessionState state, Exception exception) {
	if(session.isOpened()) {
	  Log.i(TAG,"Access Token"+ session.getAccessToken());
	  Request.newMeRequest(session, new Request.GraphUserCallback() {
		  @Override
		  public void onCompleted(GraphUser user, Response response) {
		    if (user != null) {
		    	etMail.setText(user.getProperty("email").toString());
		    	etName.setText(user.getName());
		    	etUserName.setText(user.getUsername());
		    	etWebPage.setText(user.getLink());
		    	etWebPage.setTextAppearance(ctx, android.R.style.TextAppearance_Small);
		    	imgLoader.display("http://graph.facebook.com/"+user.getId()+"/picture?type=large", ivProfile);
		    }
		  }
		}).executeAsync();
	  session.closeAndClearTokenInformation();
	}
  }
  
  @Override
  public void onError(FacebookException error) {
	  ErrorManager.show(error.getMessage(), this);
  }


}
