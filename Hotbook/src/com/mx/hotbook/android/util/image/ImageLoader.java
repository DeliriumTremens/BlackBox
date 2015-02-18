package com.mx.hotbook.android.util.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
  




import com.mx.hotbook.android.R;
import com.mx.hotbook.android.constant.Config;
import com.mx.hotbook.android.util.Utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
  
public class ImageLoader {
  
  private MemoryCache memoryCache=new MemoryCache();
  private FileCache fileCache;
  private Map<ImageView, String> imageViews = null;
  private ExecutorService executorService;
  private int stub_id = R.drawable.ic_launcher;;
  
  {
   imageViews=Collections.synchronizedMap(new WeakHashMap<ImageView, String>());  
  }
  
  public ImageLoader(Context context){
    fileCache=new FileCache(context);
    executorService=Executors.newFixedThreadPool(5);
  }
  
  public void display(String url, ImageView imageView){
    imageViews.put(imageView, url);
    Bitmap bitmap=memoryCache.get(url);
    if(bitmap!=null){
      imageView.setImageBitmap(bitmap);
    } else {
        queuePhoto(url, imageView);
        imageView.setImageResource(stub_id);
    }
  }
  
  public void clearCache() {
    memoryCache.clear();
    fileCache.clear();
  }
  
  private void queuePhoto(String url, ImageView imageView){
    PhotoToLoad p=new PhotoToLoad(url, imageView);
    executorService.submit(new PhotosLoader(p));
  }
  
  private Bitmap getBitmap(String url){
	File f=fileCache.getFile(url);
	Bitmap b = decodeFile(f);
	Bitmap bitmap=null;
	HttpURLConnection conn = null;
	InputStream is=null;
	OutputStream os = null;
	if(b!=null){
	  return b;
	}
	try {
	     conn = getHttpConnection(url);
	     is=conn.getInputStream();
	     os = new FileOutputStream(f);
	     Utilities.CopyStream(is, os);
	     os.close();
	     bitmap = decodeFile(f);
	     return bitmap;
	} catch (Exception ex){
	     ex.printStackTrace();
	     return null;
	} finally{
	    try{
	    	is.close();
	    	conn.disconnect();
	    } catch(Exception ignored){}
	}
  }
	  
  private HttpURLConnection getHttpConnection(String url) throws IOException{
	HttpURLConnection conn = null;
	URL imageUrl = null;
	imageUrl = new URL(url);
	conn = (HttpURLConnection)imageUrl.openConnection();
	if(conn.getHeaderField("Location") != null){
	  imageUrl = new URL(conn.getHeaderField("Location"));
	  conn.disconnect();
	  conn = (HttpURLConnection) imageUrl.openConnection();
	}
	conn.setConnectTimeout(Config.URL_CONNECTION_CONNECT_TIMEOUT);
	conn.setReadTimeout(Config.URL_CONNECTION_READ_TIMEOUT);
	conn.setInstanceFollowRedirects(false);
	return conn;
  }
  
  private Bitmap decodeFile(File f){
	Bitmap bm = null;
    try {
    	 BitmapFactory.Options o2 = null;
    	 int scale=Config.IMG_SCALE, width_tmp = 0, height_tmp = 0;
         BitmapFactory.Options o = new BitmapFactory.Options();
         o.inJustDecodeBounds = true;
         BitmapFactory.decodeStream(new FileInputStream(f),null,o);
         width_tmp=o.outWidth;
         height_tmp=o.outHeight;
         while(true){
           if(width_tmp/2<Config.IMG_REQUIRED_SIZE 
        		   || height_tmp/2<Config.IMG_REQUIRED_SIZE){
                    break;
           }
           width_tmp/=2;
           height_tmp/=2;
           scale*=2;
         }
         o2 = new BitmapFactory.Options();
         o2.inSampleSize=scale;
         bm = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
     } catch (Exception ex) {
    	 ex.printStackTrace();
     }
     return bm;
  }
  
  private boolean imageViewReused(PhotoToLoad photoToLoad){
	String tag=imageViews.get(photoToLoad.imageView);
	if(tag==null || !tag.equals(photoToLoad.url)){
	  return true;
	}
	return false;
  }
  
  private class PhotoToLoad{
    public String url;
    public ImageView imageView;
    public PhotoToLoad(String u, ImageView i){
      url=u;
      imageView=i;
    }
  }
  
  private class PhotosLoader implements Runnable {
    PhotoToLoad photoToLoad;
    PhotosLoader(PhotoToLoad photoToLoad){
      this.photoToLoad=photoToLoad;
    }
  
    @Override
    public void run() {
      Bitmap bmp = null;
      BitmapDisplayer bd = null;
      Activity a = null;
      if(imageViewReused(photoToLoad)){
        return;
      }
      bmp = getBitmap(photoToLoad.url);
      memoryCache.put(photoToLoad.url, bmp);
      if(imageViewReused(photoToLoad)){
        return;
      }
      bd=new BitmapDisplayer(bmp, photoToLoad);
      a=(Activity)photoToLoad.imageView.getContext();
      a.runOnUiThread(bd);
    }
  }
  
  private class BitmapDisplayer implements Runnable{
    Bitmap bitmap;
    PhotoToLoad photoToLoad;
    public BitmapDisplayer(Bitmap b, PhotoToLoad p){
       bitmap=b;photoToLoad=p;
    }
    public void run(){
      if(imageViewReused(photoToLoad)){
        return;
      }
      if(bitmap!=null){
         photoToLoad.imageView.setImageBitmap(bitmap);
      } else {
         photoToLoad.imageView.setImageResource(stub_id);
      }
    }
  }
  
}
