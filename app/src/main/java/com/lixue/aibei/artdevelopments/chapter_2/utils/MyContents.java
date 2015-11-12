package com.lixue.aibei.artdevelopments.chapter_2.utils;

import android.os.Environment;

/**
 * Created by Administrator on 2015/11/12.
 */
public class MyContents {
    public static final String CHAPTER_2_PATH = Environment.getExternalStorageDirectory().getPath()+"/chapter2/";
    public static final String CACHE_FILE_PATH = CHAPTER_2_PATH + "usercache";
    public static final int MSG_FROM_CLIENT = 0 ;
    public static final int MSG_FROM_SERVER = 1;
}
