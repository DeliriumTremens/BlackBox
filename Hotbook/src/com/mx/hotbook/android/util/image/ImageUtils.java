package com.mx.hotbook.android.util.image;

import java.io.FileNotFoundException;

import javax.microedition.khronos.opengles.GL10;

import com.mx.hotbook.android.util.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.PorterDuff.Mode;
import android.graphics.RectF;
import android.net.Uri;
import android.opengl.GLUtils;
import android.util.TypedValue;

public class ImageUtils {
	
  public static Bitmap getImageFromUri(Uri uri, Context ctx, int roundDp) 
		                                   throws FileNotFoundException {
    BitmapFactory.Options o = new BitmapFactory.Options();
    BitmapFactory.Options o2 = null;
    Bitmap result = null;
    final int REQUIRED_SIZE = 140;
    int width_tmp = 0, height_tmp = 0;
    int scale = 1;
    o.inJustDecodeBounds = true;
    BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri)
    		                                                     , null, o);
    width_tmp = o.outWidth;
    height_tmp = o.outHeight;
    while (true) {
      if(width_tmp / 2 < REQUIRED_SIZE
         || height_tmp / 2 < REQUIRED_SIZE) {
         break;
      }
      width_tmp /= 2;
      height_tmp /= 2;
      scale *= 2;
    }
    o2 = new BitmapFactory.Options();
    o2.inSampleSize = scale;
    result = BitmapFactory.decodeStream(ctx.getContentResolver()
    		                   .openInputStream(uri), null, o2);
    if(roundDp > 0){
      result = getCroppedBitmap(result, ctx, roundDp);
    }
    return result;
  }
  
  public static Bitmap getCroppedBitmap(Bitmap bitmap, Context ctx, int roundDp) {
	  Bitmap result = null;
	  int roundPx = Utilities.dpToPx(roundDp, ctx);
	  int width = bitmap.getWidth(), heigth = bitmap.getHeight();
	    try {
	    	if(width > roundPx){
	    		width = roundPx;
	    	}
	    	if(heigth > roundPx){
	    		heigth = roundPx;
	    	}
	        result = Bitmap.createBitmap(width, heigth,Bitmap.Config.ARGB_8888);
	        Canvas canvas = new Canvas(result);

	        int color = 0xff424242;
	        Paint paint = new Paint();
	        Rect rect = new Rect(0, 0,  width, heigth);
	        RectF rectF = new RectF(rect);
	        paint.setAntiAlias(true);
	        canvas.drawARGB(0, 0, 0, 0);
	        paint.setColor(color);
	        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

	        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	        canvas.drawBitmap(bitmap, rect, rect, paint);

	    } catch (NullPointerException e) {
	    } catch (OutOfMemoryError o) {
	    }
	    return result;
  }

}
