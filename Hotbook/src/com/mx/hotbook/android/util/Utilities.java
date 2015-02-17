package com.mx.hotbook.android.util;

import java.io.InputStream;
import java.io.OutputStream;

import android.text.TextUtils;
  
public class Utilities {
	
  public static void CopyStream(InputStream is, OutputStream os){
    int buffer_size=1024;
    byte[] bytes = null;
    int count = 0;
    try{
        bytes=new byte[buffer_size];
        for(;;){
          count=is.read(bytes, 0, buffer_size);
          if(count==-1){
             break;
          }
          os.write(bytes, 0, count);
        }
    }
    catch(Exception ex){}
  }
  
  public final static boolean isValidEmail(CharSequence target) {
	return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS
			                                     .matcher(target).matches();
  }
  
  public final static boolean isValidPhone(CharSequence target) {
	return !TextUtils.isEmpty(target) && android.util.Patterns.PHONE
			                             .matcher(target).matches();
  }
}