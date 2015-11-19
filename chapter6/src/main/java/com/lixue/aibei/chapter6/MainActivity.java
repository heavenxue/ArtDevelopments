package com.lixue.aibei.chapter6;

import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixue.aibei.chapter6.ui.CustomDrawable;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";

    private ImageView clipImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.test_size);
        Drawable drawable = textView.getBackground();
        Log.e(TAG, "bg:" + drawable + "w:" + drawable.getIntrinsicWidth() + " h:" + drawable.getIntrinsicHeight());
        CustomDrawable customDrawable = new CustomDrawable(R.color.light_green);
        findViewById(R.id.test_custom_drawable).setBackground(customDrawable);
        //
        clipImageview = (ImageView) findViewById(R.id.test_clip);
        final ClipDrawable clipDrawable = (ClipDrawable) clipImageview.getDrawable();
        Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1){
                    clipDrawable.setLevel(clipDrawable.getLevel() + 200);
                }
            }
        };
        final Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 1;
                if (clipDrawable.getLevel() >= 10000){
                    timer.cancel();
                }
            }
        },0,300);

    }

}
