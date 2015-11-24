package com.lixue.aibei.chapter7;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class DemoActivity_1 extends ActionBarActivity {
    private static final String TAG = "DemoActivity_1";
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_activity_1);
        initView();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ObjectAnimator.ofInt(new ViewWrapper(button),"width",500).setDuration(5000).start();
                performAnimate(button,button.getWidth(),500);
            }
        });
    }

    private void initView(){
        button = (Button) findViewById(R.id.btn1);
    }

    private static class ViewWrapper{
        private View mTarget;
        public ViewWrapper(View button){
            this.mTarget = button;
        }
        public void setWidth(int width){
            if (mTarget != null){
                ViewGroup.LayoutParams layoutParams = mTarget.getLayoutParams();
                layoutParams.width = width;
//                mTarget.setLayoutParams(layoutParams);
                //当view确定自身已经不再适合现有的区域时，该view本身调用这个方法要求parent view重新调用他的onMeasure
                // onLayout来对重新设置自己位置。特别的当view的layoutparameter发生改变，并且它的值还没能应用到view上，
                // 这时候适合调用这个方法。
                mTarget.requestLayout();
            }
        }
        public int getWidth(){
            if (mTarget != null){
                ViewGroup.LayoutParams params = mTarget.getLayoutParams();
                return params.width;
            }
            return 0;
        }
    }

    private void performAnimate(final View view, final int start, final int end){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1,100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            //持有一个IntEvaluator对象（估值器），方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //获得当前动画的进度值，整型，1~100之间
                int currentValue = (int) valueAnimator.getAnimatedValue();
                Log.d(TAG,"current value :" + currentValue);
                //获得当前进度占整个动画过程的比例，浮点型，0~1之间
                float propertional = valueAnimator.getAnimatedFraction();
                //直接调用整型估值器，通过比例计算出宽度，然后再设给Button
                view.getLayoutParams().width = mEvaluator.evaluate(propertional,start,end);
                view.requestLayout();
            }
        });
        valueAnimator.setDuration(5000).start();
    }
}
