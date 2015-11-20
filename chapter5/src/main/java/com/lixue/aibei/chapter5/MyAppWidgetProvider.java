package com.lixue.aibei.chapter5;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/11/20.
 */
public class MyAppWidgetProvider extends AppWidgetProvider {
    public static final String TAG = "MyAppWidgetProvider";
    public static final String CLICK_ACTION = "com.lixue.chapter_5.actiion.click";

    public MyAppWidgetProvider(){
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i(TAG,"onReceive : action = "+intent.getAction());
        //这里判断是自己的action，做自己的事情，比如小部件被单击了要干什么，这里是做一个动画效果
        if (intent.getAction().equals(CLICK_ACTION)){
            Toast.makeText(context,"clicked it",Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap srcBitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.m);
                    AppWidgetManager manager = AppWidgetManager.getInstance(context);
                    for (int i = 0;i < 37;i++){
                        float degree = (i * 10 ) % 360;
                        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget);
                        remoteViews.setImageViewBitmap(R.id.image, rotateBitmap(context, srcBitmap, degree));
                        Intent intent1 = new Intent();
                        intent1.setAction(CLICK_ACTION);
                        PendingIntent pt = PendingIntent.getBroadcast(context,0,intent1,0);
                        remoteViews.setOnClickPendingIntent(R.id.image,pt);
                        manager.updateAppWidget(new ComponentName(context, MyAppWidgetProvider.class), remoteViews);
                        SystemClock.sleep(30);
                    }
                }
            }).start();
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        int length = appWidgetIds.length;
        if (length >= 0){
            for(int i = 0 ;i <length;i++){
                RemoteViews remoteview = new RemoteViews(context.getPackageName(),R.layout.widget);
                Intent intent = new Intent();
                intent.setAction(CLICK_ACTION);
                PendingIntent pt = PendingIntent.getBroadcast(context,0,intent,0);
                remoteview.setOnClickPendingIntent(R.id.image,pt);
                appWidgetManager.updateAppWidget(appWidgetIds[i],remoteview);
            }
        }
    }

    /**
     * 根据旋转度数旋转图片
     * @param context
     * @param srcBitmap
     * @param degree
     * @return
     */
    private Bitmap rotateBitmap(Context context,Bitmap srcBitmap,float degree){
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap tmpBitmap = Bitmap.createBitmap(srcBitmap,0,0,srcBitmap.getWidth(),srcBitmap.getHeight(),matrix,true);
        return tmpBitmap;
    }
}
