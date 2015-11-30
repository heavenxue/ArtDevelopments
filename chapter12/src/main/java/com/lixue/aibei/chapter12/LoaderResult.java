package com.lixue.aibei.chapter12;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * 图片加载结果
 * Created by Administrator on 2015/11/30.
 */
public class LoaderResult {
    public ImageView imageView;
    public String Uri;
    public Bitmap bitmap;
    public LoaderResult(ImageView imageView,String Uri,Bitmap bitmap){
        this.imageView = imageView;
        this.Uri = Uri;
        this.bitmap = bitmap;
    }

}
