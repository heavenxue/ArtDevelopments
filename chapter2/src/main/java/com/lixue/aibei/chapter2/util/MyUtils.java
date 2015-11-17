package com.lixue.aibei.chapter2.util;

import android.app.ActivityManager;
import android.content.Context;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2015/11/17.
 */
public class MyUtils {
    /**
     * 获取当前进程的名称
     * @return
     */
    public static String getProcessName(Context context,int pid){
        Context mContext =  context.getApplicationContext();
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = activityManager.getRunningAppProcesses();
        if (runningAppProcessInfos == null) return "";
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessInfos){
            if (runningAppProcessInfo.pid == pid){
                return runningAppProcessInfo.processName;
            }
        }
        return "";
    }

    /**
     * 关闭
     * @param closeable
     */
    public static void close(Closeable closeable){
        if (closeable == null){
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在线程中执行
     * @param runnable
     */
    public static void executeInThread(Runnable runnable){
        new Thread(runnable).start();
    }
}
