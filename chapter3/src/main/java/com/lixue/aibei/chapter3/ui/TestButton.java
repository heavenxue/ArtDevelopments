package com.lixue.aibei.chapter3.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import com.nineoldandroids.view.ViewHelper;

/**
 * 自定义button，可以滑动的
 * Created by Administrator on 2015/11/17.
 */
public class TestButton extends Button {
    private float lastX;
    private float lastY;

    public TestButton(Context context) {
        super(context);
    }

    public TestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float dismenX = x - lastX;
                float dismenY = y - lastY;
//                scrollTo((int)x,(int) dismenX);
//                scrollTo((int)y,(int) dismenY);
//                ViewGroup.LayoutParams params = getLayoutParams();
//                params.width = getWidth() + (int)dismenX;
//                params.height = getHeight() + (int)dismenY;
//                setLayoutParams(params);
//                invalidate();
                float translationX = ViewHelper.getTranslationX(this) + dismenX;
                float translationY = ViewHelper.getTranslationY(this) + dismenY;
                ViewHelper.setTranslationX(this,translationX);
                ViewHelper.setTranslationY(this,translationY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        lastX = x;
        lastY = y;
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }
}
