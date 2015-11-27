package com.lixue.aibei.chapter12;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity"
            ;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView(){
        imageView = (ImageView) findViewById(R.id.myimage);
    }

    private void initData(){
        imageView.setImageBitmap(decodeSampleBitmapFromResource(getResources(), R.mipmap.image2, 100, 100));
    }

    private static Bitmap decodeSampleBitmapFromResource(Resources res,int resId,int reqWidth,int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,resId,options);//这里的参数options一定要带上，不要落下
        int height = options.outHeight;
        int width = options.outHeight;
        int inSampleSize = 1;
        Log.d(TAG,"outHeight:" + height);
        Log.d(TAG,"outWidth:"+width);
        /**计算采样率，总应为2的指数，如果外界传递给系统一个不为2的指数的，那么系统会向下取整并选择一个接近2的指数来代替**/
        if (height > reqHeight || width > reqWidth){
            final int halfHeight = height /2;
            final int halfWidth = width /2;
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth){
                inSampleSize *= 2;
            }
        }
        Log.d(TAG,"InSampleSize:" + inSampleSize);
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res,resId,options);
    }
}
