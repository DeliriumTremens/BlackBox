package com.mx.hotbook.android.ui.core;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mx.hotbook.android.R;
import com.mx.hotbook.android.constant.Config;
import com.mx.hotbook.android.ui.AbstractUI;
import com.mx.hotbook.android.util.ws.RestClient;

public class Home extends AbstractUI {

  @Override
  public View getLayout(LayoutInflater inflater, ViewGroup container) {
	View view = inflater.inflate(R.layout.home, container, false);
	callNewsFeedService();
	return view;
  }
  
  private void callNewsFeedService(){
	RequestParams params = new RequestParams();
	params.add("idUsuario", String.valueOf(user.getId()));
	RestClient.post("newsfeed", params, new JsonHttpResponseHandler() {
       @Override
       public void onSuccess(int statusCode, Header[] headers
    		                         , JSONObject response) {
    	 String status = null;
    	 try{
    		 status = response.getString("status");
    		 if(status.equals(Config.WS_STATUS_OK)){
			    System.out.println();
    		 } else {
    			 showMessage(status);
    		 }
		 } catch (JSONException e) {
			e.printStackTrace();
			showMessage(R.string.unexpectedError);
		}
       }
       @Override
       public void onSuccess(int statusCode, Header[] headers
    		                         , JSONArray response) {
    	LinearLayout container = (LinearLayout) rootView.findViewById(R.id.feedContainer);
   		LayoutInflater inflater = LayoutInflater.from(ctx);

   		View feed = null;
        for(int i = 0; i<response.length() ; i++){
        	feed = inflater.inflate(R.layout.home_feed_fragment, container, true);
        }
       }
       @Override
       public void onFailure(int statusCode, Header[] headers, java.lang.Throwable throwable, JSONObject response) {
    	   showMessage(response.toString());
       }
       @Override
       public void onFailure(int statusCode, Header[] headers, java.lang.Throwable throwable, JSONArray  response) {
    	   showMessage(response.toString());
       }
       @Override
       public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
    	   showMessage(responseString);
       }
          
    });
  }
}
