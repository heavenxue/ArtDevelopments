package com.lixue.aibei.chapter2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lixue.aibei.chapter2.model.User;
import com.lixue.aibei.chapter2.util.MyConstants;
import com.lixue.aibei.chapter2.util.MyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class SecondActivity extends Activity {
    private static final String TAG = "SecondActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView = (TextView) findViewById(R.id.tv_show);
        User user =(User) getIntent().getSerializableExtra("extra_user");
        Log.i(TAG, "收到的User对象，用户id:" + user.userId + ",用户名：" + user.userName + "是位：" + ((user.isMale == true) ? "先生" : "女士"));
        Intent intent = new Intent();
        intent.setClass(SecondActivity.this, ThirdActivity.class);
        intent.putExtra("time", System.currentTimeMillis());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = (User) getIntent().getSerializableExtra("extra_user");
        textView.setText("收到的user对象: 用户id:"+user.userId+",用户名："+user.userName+"是位："+ ((user.isMale == true) ? "先生" : "女士"));
        recoverFromFile();
    }

    private void recoverFromFile() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                User user = null;
                File cachedFile = new File(MyConstants.CACHE_FILE_PATH);
                if (cachedFile.exists()) {
                    ObjectInputStream objectInputStream = null;
                    try {
                        objectInputStream = new ObjectInputStream(new FileInputStream(cachedFile));
                        user = (User) objectInputStream.readObject();
                        Log.d(TAG, "recover user:" + user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        MyUtils.close(objectInputStream);
                    }
                }
            }
        }).start();
    }
}
