package com.mx.hotbook.android.util;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mx.hotbook.android.R;
import com.mx.hotbook.android.constant.Config;
import com.mx.hotbook.android.ui.AbstractUI;
import com.mx.hotbook.android.ui.util.ErrorManager;

public abstract class RestResponseHandler extends JsonHttpResponseHandler {
	
  private AbstractUI ctx = null;
	
  public RestResponseHandler (AbstractUI ctx){
	this.ctx = ctx;
  }
	
  public void onSuccess(JSONObject response)  throws JSONException{}
  public void onSuccess(JSONArray response)  throws JSONException{}
  
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
		ctx.hideLoader();
	}
  }
  
  @Override
  public final void onSuccess(int statusCode, Header[] headers
		                         , JSONArray response) {
	  try {
		  onSuccess(response);
	  } catch (Exception e) {
			e.printStackTrace();
			ErrorManager.show(R.string.errUnexpected, ctx);
		} finally{
			ctx.hideLoader();
		}
  }

  @Override
  public final void onFailure(int statusCode, Header[] headers
		       , String responseString, Throwable throwable) {
	  ErrorManager.show(R.string.errCommunications, ctx, statusCode);
  }  
  
  @Override
  public final void onFailure(int statusCode, Header[] headers
		                 , java.lang.Throwable throwable, JSONObject response) {
	if(response != null){
		ErrorManager.show(response.toString(),ctx , statusCode);
	} else {
		ErrorManager.show(R.string.errNull, ctx);
    }
  }
  @Override
  public final void onFailure(int statusCode, Header[] headers
		                 , java.lang.Throwable throwable, JSONArray  response) {
	if(response != null){
		ErrorManager.show(response.toString(), ctx, statusCode);
	} else {
		ErrorManager.show(R.string.errNull, ctx);
	}
  }

}
