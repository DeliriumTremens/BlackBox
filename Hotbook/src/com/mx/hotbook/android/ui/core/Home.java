package com.mx.hotbook.android.ui.core;

import org.json.JSONArray;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.loopj.android.http.RequestParams;
import com.mx.hotbook.android.R;
import com.mx.hotbook.android.ui.AbstractUI;
import com.mx.hotbook.android.util.RestResponseHandler;
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
	RestClient.post("newsfeed", params, new RestResponseHandler(this) {
       @Override
       public void onSuccess(JSONArray response) {
    	LinearLayout container = (LinearLayout) rootView.findViewById(R.id.feedContainer);
   		LayoutInflater inflater = LayoutInflater.from(ctx);

   		View feed = null;
        for(int i = 0; i<response.length() ; i++){
        	feed = inflater.inflate(R.layout.home_feed_fragment, container, true);
        }
       }
          
    });
  }
}
