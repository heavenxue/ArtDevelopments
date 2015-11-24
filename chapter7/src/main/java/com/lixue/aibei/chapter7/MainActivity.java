package com.lixue.aibei.chapter7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClick(View v){
        if (v.equals(findViewById(R.id.button1))){
            Intent intent = new Intent(MainActivity.this,TestActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim);
        }else if(v.equals(findViewById(R.id.button2))){
            Intent intent = new Intent(MainActivity.this,DemoActivity_1.class);
            startActivity(intent);
        }else if(v.equals(findViewById(R.id.button3))){
            Intent intent = new Intent(MainActivity.this,DemoActivity_2.class);
            startActivity(intent);
        }

    }
}
