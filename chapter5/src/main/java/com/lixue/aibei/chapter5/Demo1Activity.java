package com.lixue.aibei.chapter5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;


public class Demo1Activity extends ActionBarActivity implements View.OnClickListener{
    private Button button1;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        initView();
    }

    private void initView(){
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
    }

    private void showNormalNotifyBar(){
        Notification notify = new Notification();
        notify.icon = R.mipmap.image1;
        notify.tickerText = "hello world";
        notify.when = System.currentTimeMillis();
        notify.flags = Notification.FLAG_AUTO_CANCEL;
        Intent  intent = new Intent(this,Demo2Activity.class);
        PendingIntent pe = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentIntent(pe).setContentText("hello world").setContentTitle("system notification")
        .setSmallIcon(R.mipmap.image1).setWhen(System.currentTimeMillis()).setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.image2));
        notify = builder.build();
//        notification.setLatestEventInfo(this, "chapter_5", "this is notification.", pe);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notify);
    }

    private void showCustomNotifyBar(){
        Notification notification = new Notification();
        notification.icon = R.mipmap.image1;
        notification.tickerText = "hello world";
        notification.when = System.currentTimeMillis();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        Intent  intent = new Intent(this,Demo2Activity.class);
        RemoteViews remoteViews = new RemoteViews(getPackageName(),R.layout.layout_notification);
        remoteViews.setTextViewText(R.id.msg,"chapter_5");
        remoteViews.setImageViewResource(R.id.icon,R.mipmap.image2);

        PendingIntent pe = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.open_activity2,pe);

        notification.contentView = remoteViews;
        notification.contentIntent = pe;

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(2, notification);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button1){
            showNormalNotifyBar();
        }else if (view.getId() == R.id.button2){
            showCustomNotifyBar();
        }
    }
}
