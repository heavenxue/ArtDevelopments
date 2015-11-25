package com.lixue.aibei.chapter2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by Administrator on 2015/11/25.
 */
public class BookProvider extends ContentProvider {
    private static final String TAG = "BookProvider";

    public static final String AUTHORITY = "com.lixue.aibei.chapter2.book.provider";
    //定义几个URI
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");
    //定义标的code
    public static final  int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY,"book",BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHORITY,"user",USER_URI_CODE);
    }

    private SQLiteDatabase mDb;
    private Context context;

    /**获取表名**/
    private String getTableName(Uri uri){
        String tablename = "";
        switch (sUriMatcher.match(uri)){
            case BOOK_URI_CODE:
                tablename = DbOpenHelper.BOOK_TABLE_NAME;
                break;
            case USER_URI_CODE:
                tablename = DbOpenHelper.USER_TABLE_NAME;
                break;
            default:break;
        }
        return tablename;
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG,"onCreate,current Thread:"+Thread.currentThread().getName());
        //ContentProvider创建时，初始化数据库。
        context = getContext();
        /**注意：这里仅仅是为了演示，实际使用中不推荐在主线程中进行耗时的数据库操作**/
        initProviderData();
        return true;
    }

    private void initProviderData(){
        mDb = new DbOpenHelper(context).getWritableDatabase();
        mDb.execSQL("delete from " + DbOpenHelper.BOOK_TABLE_NAME);
        mDb.execSQL("delete from " +DbOpenHelper.USER_TABLE_NAME);
        mDb.execSQL("insert into book values(3,'Android');");
        mDb.execSQL("insert into book values(4,'IOS');");
        mDb.execSQL("insert into book values(5,'Html5');");
        mDb.execSQL("insert into user values(1,'jake',1);");
        mDb.execSQL("insert into user values(2,'jasmine',0);");


    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Log.d(TAG,"querey,current Thread:"+Thread.currentThread().getName());
        String name = getTableName(uri);
        if (name==null || "".equals(name)){
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        return mDb.query(name,strings,s,strings1,null,null,s1,null);
    }

    @Override
    public String getType(Uri uri) {
        Log.d(TAG,"getType");
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Log.d(TAG,"insert");
        String name = getTableName(uri);
        if (name==null || "".equals(name)){
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        mDb.insert(name,null,contentValues);
        context.getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        Log.d(TAG,"delete");
        String name = getTableName(uri);
        if (name==null || "".equals(name)){
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        int count = mDb.delete(name,s,strings);
        if (count > 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        Log.d(TAG,"update");
        String name = getTableName(uri);
        if (name==null || "".equals(name)){
            throw new IllegalArgumentException("Unsupported URI:" + uri);
        }
        int row = mDb.update(name,contentValues,s,strings);
        if (row > 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return row;
    }
}
