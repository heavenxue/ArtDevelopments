package com.lixue.aibei.chapter7;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class TestActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);//用于activity间的切换动画
    }
}
