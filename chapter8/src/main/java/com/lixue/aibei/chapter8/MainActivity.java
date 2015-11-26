package com.lixue.aibei.chapter8;

import android.app.Dialog;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private Button mButton;
    private WindowManager.LayoutParams mLayoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int x = (int) motionEvent.getRawX();
                int y =(int) motionEvent.getRawY();
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                    mLayoutParams.x = x;
                    mLayoutParams.y = y;
                    getWindowManager().updateViewLayout(mButton,mLayoutParams);
                }
                return false;
            }
        });
    }
    private void initView(){
        //利用WindowManager创建Button
        mButton = new Button(this);
        mButton.setText("button");
        mLayoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,0,0, PixelFormat.TRANSLUCENT);
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE| WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        mLayoutParams.gravity = Gravity.LEFT|Gravity.TOP;
        mLayoutParams.x = 100;
        mLayoutParams.y = 300;
        getWindowManager().addView(mButton,mLayoutParams);
        //创建Toast,利用系统类型的Window创建Dialog
        Dialog dialog = new Dialog(this.getApplicationContext());
//        Dialog dialog = new Dialog(MainActivity.this);
        TextView textView = new TextView(this);
        textView.setText("This is toast!");
        dialog.setContentView(textView);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        dialog.show();


    }

    @Override
    protected void onDestroy() {
        try {
            getWindowManager().removeView(mButton);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
