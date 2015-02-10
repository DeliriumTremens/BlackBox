package com.mx.hotbook.android.util.ws;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

import com.mx.hotbook.android.constant.Config;

import android.os.Build;

public class RestUtil {
	
  public static JSONObject call(String serviceUrl) {
	HttpURLConnection urlConnection = null;
	URL urlToRequest = null;
	int statusCode = 0;
	InputStream in = null;
	disableConnectionReuseIfNecessary(); 
	try {
	     urlToRequest = new URL(serviceUrl);
	     urlConnection = (HttpURLConnection) urlToRequest.openConnection();
	     urlConnection.setConnectTimeout(Config.URL_CONNECTION_CONNECT_TIMEOUT);
	     urlConnection.setReadTimeout(Config.URL_CONNECTION_READ_TIMEOUT);
	     statusCode = urlConnection.getResponseCode();
         if (statusCode == HttpURLConnection.HTTP_OK) {	     
	        in = new BufferedInputStream(urlConnection.getInputStream());
	        return new JSONObject(getResponseText(in));
         }
	} catch (Exception e) {
           e.printStackTrace();
	} finally {
	    if(urlConnection != null) {
	      urlConnection.disconnect();
	    }
	}      
	return null;
  }

  @SuppressWarnings("deprecation")
  private static void disableConnectionReuseIfNecessary() {
	if(Integer.parseInt(Build.VERSION.SDK)< Build.VERSION_CODES.FROYO) {
	  System.setProperty("http.keepAlive", "false");
	}
  }
	 
  private static String getResponseText(InputStream inStream) {
	Scanner scanner = new Scanner(inStream);
	String responseText = scanner.useDelimiter("\\A").next();
	scanner.close();
	return responseText;
  }

}
