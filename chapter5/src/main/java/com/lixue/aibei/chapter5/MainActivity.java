package com.lixue.aibei.chapter5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import com.lixue.aibei.chapter5.util.MyConstants;


public class MainActivity extends ActionBarActivity {
    private LinearLayout mRemoteViewContent;

    private BroadcastReceiver mRemoteViewReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            RemoteViews remoteViews = intent.getParcelableExtra(MyConstants.EXTRA_REMOTE_VIEWS);
            if (remoteViews != null) {
                updateUI(remoteViews);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView (){
        mRemoteViewContent = (LinearLayout) findViewById(R.id.remote_views_content);
        IntentFilter intentFilter = new IntentFilter(MyConstants.REMOTE_ACTION);
        registerReceiver(mRemoteViewReciever, intentFilter);
    }

    private void updateUI(RemoteViews remoteViews) {
//        View view = remoteViews.apply(this, mRemoteViewsContent);
        int layoutId = getResources().getIdentifier("layout_simulated_notification", "layout", getPackageName());
        View view = getLayoutInflater().inflate(layoutId, mRemoteViewContent, false);
//        remoteViews.reapply(MainActivity.this, view);
        mRemoteViewContent.addView(view);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mRemoteViewReciever);
        super.onDestroy();
    }

    public void onButtonClick(View v){
     if (v.getId() == R.id.btn1){
         Intent intent = new Intent();
         intent.setClass(MainActivity.this,Demo1Activity.class);
         startActivity(intent);
     }else if(v.getId() == R.id.btn2){
         Intent intent = new Intent();
         intent.setClass(MainActivity.this,Demo2Activity.class);
         startActivity(intent);
     }
    }
}
