package com.mx.hotbook.android.ui.util;

import com.mx.hotbook.android.ui.AbstractUI;

import android.widget.Toast;

public class ErrorManager {
	  
  
  public static void show(Integer resId, AbstractUI ctx, Object ... var){
	show(ctx.getResources().getString(resId), ctx, var);
  }
	  
  public static void show(String message, AbstractUI ctx, Object ... var){
	Toast.makeText(ctx, String.format(message, var), Toast.LENGTH_SHORT).show();
  }

}
