package com.mx.hotbook.android.ui;

import com.mx.hotbook.android.R;
import com.mx.hotbook.android.util.calligraphy.CalligraphyConfig;
import com.mx.hotbook.android.util.calligraphy.CalligraphyContextWrapper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public abstract class AbstractUI extends Activity{
	
  protected String TAG = "HotBox";
	
  static{
	  CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                       .setDefaultFontPath("fonts/SohoStd-MediumCondensed.otf")
                       .setFontAttrId(R.attr.fontPath)
                       .build()
      );
  }
  
  public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
      super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
  
}
