package com.mx.hotbook.android.ui.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.RequestParams;
import com.mx.hotbook.android.R;
import com.mx.hotbook.android.ui.AbstractUI;
import com.mx.hotbook.android.util.ws.RestClient;
import com.mx.hotbook.android.util.ws.RestResponseHandler;

import org.json.*;

public class Register extends AbstractUI {
	
  @Override
  public View getLayout(LayoutInflater inflater, ViewGroup container) {
	View view = inflater.inflate(R.layout.register, container, false);
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

}
