package com.lixue.aibei.chapter2;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.lixue.aibei.chapter2.model.User;


public class ProviderActivity extends ActionBarActivity {
    private static final String TAG = "ProviderActivity";

    private TextView tv_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        Uri bookUri = Uri.parse("content://com.lixue.aibei.chapter2.book.provider/book");
//        getContentResolver().query(uri,null,null,null,null);
//        getContentResolver().query(uri,null,null,null,null);
//        getContentResolver().query(uri,null,null,null,null);
        initview();
        //操作book表
        ContentValues values = new ContentValues();
        values.put("_id",6);
        values.put("name", "程序设计的艺术");
        getContentResolver().insert(bookUri, values);
        Cursor bookCursor = getContentResolver().query(bookUri,new String[]{"_id","name"},null,null,null);
        StringBuilder sb = new StringBuilder();
        while (bookCursor.moveToNext()){
            Book book = new Book();
            book.bookId = bookCursor.getInt(0);
            book.bookName = bookCursor.getString(1);
            Log.d(TAG,"query book :" + book.toString());
            sb.append(book.toString() + ";");
        }
        bookCursor.close();
        tv_query.setText("查询book表后，数据为："+sb.toString());
        //操作user表
        Uri userUri = Uri.parse("content://com.lixue.aibei.chapter2.book.provider/user");
        Cursor userCursor = getContentResolver().query(userUri, new String[]{"_id", "name", "sex"}, null, null, null);
        StringBuilder sbs = new StringBuilder();
        while (userCursor.moveToNext()){
            User user = new User();
            user.userId = userCursor.getInt(0);
            user.userName = userCursor.getString(1);
            user.isMale = userCursor.getInt(2) == 1?true:false;
            Log.d(TAG,"query user:" +user.toString());
        }
        userCursor.close();
    }
    private void initview(){
        tv_query = (TextView) findViewById(R.id.tv_query);
    }
}
