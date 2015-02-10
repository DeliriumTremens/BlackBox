package com.mx.hotbook.android.ui;

import android.app.Activity;
import android.os.Bundle;

public abstract class AbstractUI extends Activity{
	
  protected String TAG = "HotBox";
	
  public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
  }

}
