package com.mx.hotbook.android.constant;

import java.util.Arrays;
import java.util.List;

import android.os.Environment;

public class Config {
	
  public static final String BASE_LOCAL_PATH = Environment.getExternalStorageDirectory() + "/.hotbook";
  public static final String CACHE_LOCAL_PATH = BASE_LOCAL_PATH + "/cache";
  public static final Integer SPLASH_PERIOD = 1000;
  public static final Integer URL_CONNECTION_CONNECT_TIMEOUT = 3000;
  public static final Integer URL_CONNECTION_READ_TIMEOUT = 3000;
  public static final Integer IMG_REQUIRED_SIZE = 70;
  public static final Integer IMG_SCALE = 1;
  public static final String APP_FONT_NAME ="SohoStd-MediumCondensed";
  public static final String DRAWER_META_DATA_NAME = "hasLateralMenu";
  public static final String WS_BASE_PATH = "http://kelevrads.com/hotbook/services/";
  public static final String WS_METHOD_POSTFIX = ".php";
  public static final String WS_STATUS_OK = "ok";
  public static final List<String> FB_PERMISSIONS = Arrays.asList("public_profile","email");
  public static final int ACTIVITY_RESULT_PHOTO = 1;
  public static final int ACTIVITY_FACEBOOK_CB = 64206;
  public static final int LARGE_ROUND_DIP = 70;

}
