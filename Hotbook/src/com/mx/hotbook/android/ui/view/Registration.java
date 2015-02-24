package com.mx.hotbook.android.ui.view;

import java.io.FileNotFoundException;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
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
import com.mx.hotbook.android.util.image.ImageUtils;
import com.mx.hotbook.android.util.ws.RestClient;
import com.mx.hotbook.android.util.ws.RestResponseHandler;

import org.json.*;

public class Registration extends AbstractUI 
                          implements Session.StatusCallback, OnErrorListener{

  private EditText etMail = null;
  private EditText etUserName = null;
  private EditText etPassword = null;
  private EditText etRepeatPassword = null;
  private EditText etName = null;
  private EditText etWebPage = null;
  private EditText etBiography = null;
  private EditText etAddress = null;
  private ImageView ivProfile = null;
	
  @Override
  public View getLayout(LayoutInflater inflater, ViewGroup container) {
	View view = inflater.inflate(R.layout.registration, container, false);
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
	etAddress = (EditText) view.findViewById(R.id.etAddress);
	ivProfile = (ImageView) view.findViewById(R.id.ivProfile);
	etAddress.setOnFocusChangeListener(new OnFocusChangeListener(){
      @Override
	  public void onFocusChange(View v, boolean hasFocus) {
		if(hasFocus){
			startActivity(new Intent(ctx, RegistrationLocation.class));
		}	
	  }
	});
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
  
  public void onClickIbProfilePic (View view){
	Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
	photoPickerIntent.setType("image/*");
	startActivityForResult(photoPickerIntent, Config.ACTIVITY_RESULT_PHOTO);  
  }
  
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	switch(requestCode) { 
      case Config.ACTIVITY_RESULT_PHOTO:
        if(resultCode == RESULT_OK){  
          try {
			   ivProfile.setImageBitmap(ImageUtils.getImageFromUri(data.getData()
					                              , ctx, Config.LARGE_ROUND_DIP));
		  } catch (FileNotFoundException e) {
				e.printStackTrace();
		  }
        }
        break;
      case Config.ACTIVITY_FACEBOOK_CB : Session.getActiveSession()
                         .onActivityResult(this, requestCode, resultCode, data);
        break;
    }
	
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
		    	imgLoader.display("http://graph.facebook.com/"+user.getId()
		    	    + "/picture?type=large", ivProfile, Config.LARGE_ROUND_DIP);
		    } else {
		    	ErrorManager.show(ctx.getResources().getString(R.string
		    			         .errFBNotAvailable), (AbstractUI)ctx);
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
