package com.lixue.aibei.chapter4.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.lixue.aibei.chapter4.R;

/**
 * 一个特殊的LinearLayout,任何放入内部的clickable元素都具有波纹效果，当它被点击的时候，
 * 为了性能，尽量不要在内部放入复杂的元素
 * Created by Administrator on 2015/11/18.
 */
public class RealLayout extends LinearLayout {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private View mTouchTarget;

    //分别表示上一次滑动时的坐标
    private int mLastX = 0;
    private int mLastY = 0;
    private int mLastInterceptX = 0;
    private int mLastInterceptY = 0;
    private int[] mLocationInScreen = new int[2];


    public RealLayout(Context context) {
        super(context);
        init();
    }

    public RealLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RealLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RealLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //获取在整个屏幕内的绝对坐标，注意这个值是要从屏幕顶端算起，也就是包括了通知栏的高度。[0]代表x坐标,location [1] 代表y坐标。
        this.getLocationOnScreen(mLocationInScreen);
    }

    /**
     * 初始化
     */
    private void init(){
        //这里要重写ondraw，所以不需要此标志
        setWillNotDraw(false);
        mPaint.setColor(getResources().getColor(R.color.reveal_color));
    }


    //当绘制子view（children）时，用此方法
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    //拦截事件（外部拦截）
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    //触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
