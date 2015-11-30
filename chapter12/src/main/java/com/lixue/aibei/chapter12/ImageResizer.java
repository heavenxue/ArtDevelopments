package com.lixue.aibei.chapter12;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileDescriptor;

/**
 * 图片压缩
 * Created by Administrator on 2015/11/30.
 */
public class ImageResizer {
    private static final String TAG = "ImageResizer";

    /**
     * 解码图片
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res,int resId,int reqWidth,int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,resId,options);
        //计算采样率
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res,resId,options);
    }

    public static Bitmap decodeSampleBitmapFromFileDescriptro(FileDescriptor fd,int reqWidth,int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd,null,options);
        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd,null,options);
    }

    /**
     * 计算采样率
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        int width = options.outWidth;
        int height = options.outHeight;
        int inSmaple = 1;
        if (width >= reqWidth && height >= reqHeight){
            int halfWidth = width /2;
            int halfHeight = height /2;
            while ((halfHeight/inSmaple) >= reqHeight && (halfWidth/inSmaple) >= reqWidth){
                inSmaple *= 2;
            }
        }
        Log.d(TAG,"sampleSize:"+ inSmaple);
        return inSmaple;
    }
}
