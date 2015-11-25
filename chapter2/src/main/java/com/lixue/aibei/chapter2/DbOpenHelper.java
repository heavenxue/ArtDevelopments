package com.lixue.aibei.chapter2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 简单创建的一个数据库
 * Created by Administrator on 2015/11/25.
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME ="book_provider.db";
    public static final String BOOK_TABLE_NAME = "book";
    public static final String USER_TABLE_NAME = "user";

    private static final int DB_VERSION = 1;

    //图书和用户信息表
    private String CREATE_BOOK_TABLE= "CREATE TABLE IF NOT EXISTS " + BOOK_TABLE_NAME + " (_id INTEGER PRIMARY KEY," + "name TEXT)";
    private String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS "+ USER_TABLE_NAME + " (_id INTERGER PRIMARY KEY,"
    +"name Text," + "sex INT)";

    public DbOpenHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BOOK_TABLE);
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
