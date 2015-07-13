package com.sheepm.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImage extends ImageView {  
	  
    /** 
     * 3个构造函数 
     * @param context 
     */  
    public CircleImage(Context context) {  
        super(context);  
    }  
  
    public CircleImage(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public CircleImage(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
  
    /** 
     * 重写的ondraw方法 
     */  
    @Override  
    protected void onDraw(Canvas canvas) {  
        Drawable drawable = getDrawable();  
        if (drawable == null) {  
            return;  
        }  
  
        if (getWidth() == 0 || getHeight() == 0) {  
            return;  
        }  
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();  
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);  
  
        Bitmap roundBitmap = getCroppedBitmap(bitmap, getWidth());  
        canvas.drawBitmap(roundBitmap, 0, 0, null);  
    }  
  
    /** 
     * 对bitmap进行裁剪成圆形 
     * @param bmp 
     * @param radius 
     * @return 
     */  
    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {  
        Bitmap sbmp;  
        if (bmp.getWidth() != radius || bmp.getHeight() != radius)  
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);  
        else  
            sbmp = bmp;  
  
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),  
                Bitmap.Config.ARGB_8888);  
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());  
  
        Paint paint = new Paint();  
        paint.setAntiAlias(true);  
        paint.setFilterBitmap(true);  
        paint.setDither(true);  
        paint.setColor(Color.parseColor("#BAB399"));  
  
        Canvas c = new Canvas(output);  
        c.drawARGB(0, 0, 0, 0);  
        c.drawCircle(sbmp.getWidth() / 2 + 0.7f, sbmp.getHeight() / 2 + 0.7f,  
                sbmp.getWidth() / 2 + 0.1f, paint);  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        c.drawBitmap(sbmp, rect, rect, paint);  
  
        return output;  
    }  
  
}  
