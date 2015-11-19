package com.lixue.aibei.chapter6.ui;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * 自定义drawable,中心画圆
 * Created by Administrator on 2015/11/19.
 */
public class CustomDrawable extends Drawable {
    private Paint mPaint;

    public CustomDrawable(int color){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect rect = getBounds();
        int centerX = rect.centerX();
        int centerY = rect.centerY();
        canvas.drawCircle(centerX,centerY,Math.min(centerX,centerY)/2,mPaint);
    }

    @Override
    public void setAlpha(int i) {
        mPaint.setAlpha(i);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
