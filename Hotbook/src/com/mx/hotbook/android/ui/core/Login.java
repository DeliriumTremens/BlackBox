package com.mx.hotbook.android.ui.core;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import com.mx.hotbook.android.util.RestResponseHandler;
import com.mx.hotbook.android.util.ws.RestClient;

@SuppressWarnings("deprecation")
public class Login extends AbstractUI implements Session.StatusCallback, OnErrorListener {
  
  private EditText etMail = null;
  private EditText etPassword = null;
  
  
  @Override
  public View getLayout(LayoutInflater inflater, ViewGroup container) {
	View view = inflater.inflate(R.layout.login, container, false);
	LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
	etMail = (EditText) view.findViewById(R.id.etMail);
	etPassword = (EditText) view.findViewById(R.id.etPassword);
	authButton.setOnErrorListener(this);
	authButton.setReadPermissions(Config.FB_PERMISSIONS);
	authButton.setSessionStatusCallback(this);
	return view;
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
  }
  
  public void onClickBnLogin(View view){
	callSignUpService();
  }
  
  private void callSignUpService(){
	RequestParams params = new RequestParams();
//	params.add("username", etMail.getText().toString());
//	params.add("password", etPassword.getText().toString());
	params.add("username", "editor1");
	params.add("password", "editor1");
	RestClient.post("sign_up", params, new RestResponseHandler(this) {
       @Override
       public void onSuccess(JSONObject response) throws JSONException {
		  setSession(response.getInt("id"), etMail.getText().toString());
		  startActivity(new Intent(ctx, Home.class));
       }
    });
  }

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

  @Override
  public void onError(FacebookException error) {
	  ErrorManager.show(error.getMessage(), this);
  }
	 
}
