package com.lixue.aibei.chapter3;

import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class TestActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String TAG = "TestActivity";
    private static final int MESSAGE_SCROLL_TO = 1;
    private static final int FRAME_COUNT = 30;
    private static final int DELAYED_TIME = 33;

    private Button testButton;
    private Button button1;
    private EditText editText1;

    private int count = 0;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_SCROLL_TO:
                    count ++;
                    if (count <= FRAME_COUNT){
                        float s = (float)count / FRAME_COUNT;
                        button1.scrollTo((int)s * 100,0);
                        button1.invalidate();
                        mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO,DELAYED_TIME);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        testButton = (Button) findViewById(R.id.button2);
        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);
        editText1 = (EditText) findViewById(R.id.editText1);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Log.d(TAG, "button1.left=" + button1.getLeft());
            Log.d(TAG, "button1.x=" + button1.getX());
        }
    }

    @Override
    public void onClick(View view) {
        if (view == button1){
//             button1.setTranslationX(100);

            // Log.d(TAG, "button1.left=" + mButton1.getLeft());
            // Log.d(TAG, "button1.x=" + mButton1.getX());
//             ObjectAnimator.ofFloat(button1, "translationX", 0, 100).setDuration(1000).start();
//             ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) button1.getLayoutParams();
//             params.width += 100;
//             params.leftMargin += 100;
//             button1.requestLayout();
//             button1.setLayoutParams(params);

             final int startX = 0;
             final int deltaX = 100;
             ValueAnimator animator = ValueAnimator.ofInt(0,1).setDuration(1000);
             animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                 @Override
                 public void onAnimationUpdate(ValueAnimator animator) {
                 float fraction = animator.getAnimatedFraction();
                    button1.scrollTo(startX + (int) (deltaX * fraction), 0);
                 }
             });
             animator.start();

//            mHandler.sendEmptyMessageDelayed(MESSAGE_SCROLL_TO,DELAYED_TIME);
        }
    }
}
