package com.lixue.aibei.chapter2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.lixue.aibei.chapter2.manager.UserManager;
import com.lixue.aibei.chapter2.model.User;
import com.lixue.aibei.chapter2.util.MyConstants;
import com.lixue.aibei.chapter2.util.MyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserManager.sUserId = 2;
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, SecondActivity.class);
        User muser = new User(0, "jake", true);
//        user.book = new Book();
//        intent.putExtra("extra_user", (Parcelable) muser);
        intent.putExtra("extra_user", (Serializable) muser);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        Log.i("MainActivity","UserManager.sUserId = " + UserManager.sUserId);
        persistToFile();
        super.onResume();
    }

    private void persistToFile(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                User user = new User(1, "hello world", false);
                File dir = new File(MyConstants.CHAPTER_2_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File cachedFile = new File(MyConstants.CACHE_FILE_PATH);
                ObjectOutputStream objectOutputStream = null;
                try {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(cachedFile));
                    objectOutputStream.writeObject(user);
                    Log.d(TAG, "persist user:" + user);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    MyUtils.close(objectOutputStream);
                }
            }
        }).start();
    }
}
