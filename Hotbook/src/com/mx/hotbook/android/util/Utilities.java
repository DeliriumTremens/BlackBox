package com.mx.hotbook.android.util;

import java.io.InputStream;
import java.io.OutputStream;
  
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
}