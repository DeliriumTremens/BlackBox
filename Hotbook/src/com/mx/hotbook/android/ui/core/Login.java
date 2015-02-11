package com.mx.hotbook.android.ui.core;

import java.util.Arrays;

import org.apache.http.Header;
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
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mx.hotbook.android.R;
import com.mx.hotbook.android.constant.Config;
import com.mx.hotbook.android.ui.AbstractUI;
import com.mx.hotbook.android.util.ws.RestClient;

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
  
  public void onClickBnLogin(View view){
	EditText etMail = (EditText) rootView.findViewById(R.id.etMail);
	EditText etPassword = (EditText) rootView.findViewById(R.id.etPassword);
	callSignUpService(etMail.getText().toString(), etPassword.getText().toString());
  }
  
  private void callSignUpService(final String mail, final String password){
	RequestParams params = new RequestParams();
//	params.add("username", mail);
//	params.add("password", password);
	params.add("username", "editor1");
	params.add("password", "editor1");
	RestClient.post("sign_up", params, new JsonHttpResponseHandler() {
       @Override
       public void onSuccess(int statusCode, Header[] headers
    		                         , JSONObject response) {
    	 String status = null;
    	 try{
    		 status = response.getString("status");
    		 if(status.equals(Config.WS_STATUS_OK)){
			    setSession(response.getInt("id"), mail);
			    startActivity(new Intent(ctx, Home.class));
    		 } else {
    			 showMessage(status);
    		 }
		 } catch (JSONException e) {
			e.printStackTrace();
			showMessage(R.string.unexpectedError);
		}
       }
       @Override
       public void onFailure(int statusCode, Header[] headers
    		  , String responseString, Throwable throwable) {
    	   showMessage(responseString);
       }
          
    });
  }
	 
}
