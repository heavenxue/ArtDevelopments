package com.lixue.aibei.chapter2.util;

import android.os.Environment;

/**
 * Created by Administrator on 2015/11/17.
 */
public class MyConstants{
    public static final String CHAPTER_2_PATH = Environment
            .getExternalStorageDirectory().getPath()
            + "/artProjects/chapter_2/";

    //缓存路径
    public static final String CACHE_FILE_PATH = CHAPTER_2_PATH + "usercache";

    public static final int MSG_FROM_CLIENT = 0;
    public static final int MSG_FROM_SERVICE = 1;

}