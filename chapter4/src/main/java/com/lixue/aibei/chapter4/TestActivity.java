package com.lixue.aibei.chapter4;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;


public class TestActivity extends ActionBarActivity {

    private static final String TAG = "TestActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textView = (TextView) findViewById(R.id.myView);
        measureView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            int width = textView.getMeasuredWidth();
            int height = textView.getMeasuredHeight();
            Toast.makeText(getBaseContext(), "-----onWindowFocusChanged----我的view的测量宽高分别为：" + width + "," + height, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "-----onWindowFocusChanged----我的view的测量宽高分别为：" + width + "," + height);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        textView.post(new Runnable() {
//            @Override
//            public void run() {
//                int width = textView.getMeasuredWidth();
//                int height = textView.getMeasuredHeight();
//                Toast.makeText(getBaseContext(), "-----textView.post----我的view的测量宽高分别为：" + width + "," + height, Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "-----textView.post----我的view的测量宽高分别为：" + width + "," + height);
//            }
//        });
        ViewTreeObserver observer = textView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                textView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width = textView.getMeasuredWidth();
                int height = textView.getMeasuredHeight();
                Toast.makeText(getBaseContext(), "-----ViewTreeObserver----我的view的测量宽高分别为：" + width + "," + height, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "-----ViewTreeObserver----我的view的测量宽高分别为：" + width + "," + height);
            }
        });
    }
    private void measureView() {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(100, View.MeasureSpec.EXACTLY);
        textView.measure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "measureView, width= " + textView.getMeasuredWidth() + " height= " + textView.getMeasuredHeight());
        Toast.makeText(getBaseContext(), "-----measureView----我的view的测量宽高分别为：" + textView.getMeasuredWidth() + "," + textView.getMeasuredHeight(), Toast.LENGTH_SHORT).show();
    }
}
