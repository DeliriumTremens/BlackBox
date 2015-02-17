package com.mx.hotbook.android.util.ws;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mx.hotbook.android.R;
import com.mx.hotbook.android.constant.Config;
import com.mx.hotbook.android.ui.AbstractUI;
import com.mx.hotbook.android.ui.util.ErrorManager;

public abstract class RestResponseHandler extends JsonHttpResponseHandler {
	
  private AbstractUI ctx = null;
  private boolean showLoader = false;
  private View loader = null;
	
  public RestResponseHandler (AbstractUI ctx, boolean showLoader){
	this(ctx);
	this.showLoader = showLoader;
  }
  
  public RestResponseHandler (AbstractUI ctx){
	this.ctx = ctx;
	loader = ctx.getLoader();
  }
	
  public void onSuccess(JSONObject response)  throws JSONException{}
  public void onSuccess(JSONArray response)  throws JSONException{}
  
  
  @Override
  public final void onStart(){
	if(showLoader){
	  showLoader();  
	}
  }
  
  @Override
  final public void onSuccess(int statusCode, Header[] headers
		                              , JSONObject response) {
	String status = null;
	try {
		 status = response.getString("status");
		 if((status == null) || (status.equals(Config.WS_STATUS_OK))){
		   onSuccess(response); 
		 } else {
			 ErrorManager.show(status, ctx);
		 }
	} catch (Exception e) {
		e.printStackTrace();
		ErrorManager.show(R.string.errUnexpected, ctx);
	} finally{
		hideLoader();
	}
  }
  
  @Override
  public final void onSuccess(int statusCode, Header[] headers
		                         , JSONArray response) {
	try{
		onSuccess(response);
	} catch(Exception e) {
		e.printStackTrace();
		ErrorManager.show(R.string.errUnexpected, ctx);
	} finally{
		hideLoader();
	}
  }

  @Override
  public final void onFailure(int statusCode, Header[] headers
		       , String responseString, Throwable throwable) {
	try{
	    ErrorManager.show(R.string.errCommunications, ctx, statusCode);
	} finally{
		hideLoader();
	}
  }  
  
  @Override
  public final void onFailure(int statusCode, Header[] headers
		                 , java.lang.Throwable throwable, JSONObject response) {
	try{
	    if(response != null){
		  ErrorManager.show(response.toString(),ctx , statusCode);
	    } else {
		   ErrorManager.show(R.string.errNull, ctx);
        }
	} finally{
		hideLoader();
	}
  }
  
  @Override
  public final void onFailure(int statusCode, Header[] headers
		                 , java.lang.Throwable throwable, JSONArray  response) {
    try{
	  if(response != null){
		ErrorManager.show(response.toString(), ctx, statusCode);
	  } else {
		ErrorManager.show(R.string.errNull, ctx);
	  }
    } finally{
	  hideLoader();
    }
  }
  
  private void hideLoader(){
	if((loader != null)){
	  loader.setVisibility(View.GONE);
	}
  }
  
  private void showLoader(){
	if((loader != null)){
	  loader.setVisibility(View.VISIBLE);
	} 
  }

}
