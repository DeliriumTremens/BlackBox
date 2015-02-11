package com.mx.hotbook.android.ui.core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mx.hotbook.android.R;
import com.mx.hotbook.android.ui.AbstractUI;
import com.mx.hotbook.android.util.ws.RestClient;

import org.json.*;
import org.apache.http.Header;

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
	  RestClient.post("sign_up", params, new JsonHttpResponseHandler() {
          @Override
          public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
              System.out.println();
          }
          @Override
          public void onFailure(int statusCode, Header[] headers, java.lang.Throwable throwable, JSONObject response) {
              System.out.println();
          }
          @Override
          public void onFailure(int statusCode, Header[] headers, java.lang.Throwable throwable, JSONArray  response) {
              System.out.println();
          }
          @Override
          public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
              System.out.println();
          }
          
      });
  }

}
