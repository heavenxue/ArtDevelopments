package com.lixue.aibei.chapter5;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.*;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.lixue.aibei.chapter5.util.MyConstants;


public class Demo2Activity extends ActionBarActivity {
    private static final String TAG = "Demo2Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);
        Log.d(TAG, "onCreate");
        Toast.makeText(this, getIntent().getStringExtra("sid"),Toast.LENGTH_SHORT).show();
        initView();
    }
    private void initView() {
    }

    public void onButtonClick(View v) {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_simulated_notification);
        remoteViews.setTextViewText(R.id.msg, "msg from process:" + android.os.Process.myPid());
        remoteViews.setImageViewResource(R.id.icon, R.mipmap.image1);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, new Intent(this, TestActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent openActivity2PendingIntent = PendingIntent.getActivity( this, 0, new Intent(this, Demo2Activity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.item_holder, pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.open_activity2, openActivity2PendingIntent);
        Intent intent = new Intent(MyConstants.REMOTE_ACTION);
        intent.putExtra(MyConstants.EXTRA_REMOTE_VIEWS, remoteViews);
        sendBroadcast(intent);
    }
}
