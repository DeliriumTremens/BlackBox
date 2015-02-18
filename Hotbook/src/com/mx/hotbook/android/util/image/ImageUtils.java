package com.mx.hotbook.android.util.image;

import java.io.FileNotFoundException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.PorterDuff.Mode;
import android.net.Uri;

public class ImageUtils {
	
  public static Bitmap getImageFromUri(Uri uri, Context ctx, boolean isCroped) 
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
    if(isCroped){
      result = getCroppedBitmap(result);
    }
    return result;
  }
  
  public static Bitmap getCroppedBitmap(Bitmap bitmap) {
	Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight()
			                    , android.graphics.Bitmap.Config.ARGB_8888);
	Canvas canvas = new Canvas(output);
	int color = 0xff424242;
	Paint paint = new Paint();
	Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	paint.setAntiAlias(true);
	canvas.drawARGB(0, 0, 0, 0);
	paint.setColor(color);
	canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
	                                  bitmap.getWidth() / 2, paint);
	paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	canvas.drawBitmap(bitmap, rect, rect, paint);
	return output;
  }

}
