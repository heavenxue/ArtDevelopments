package com.lixue.aibei.artdevelopments.chapter_2.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * ������
 * Created by Administrator on 2015/11/12.
 */
public class MyUtils {
    /**
     * ���ָ��pid�Ľ�����
     * @param context
     * @param pid
     * @return
     */
    public static String getProcessName(Context context,int pid){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processes = activityManager.getRunningAppProcesses();
        if (processes == null) return null;
        for (ActivityManager.RunningAppProcessInfo processInfo : processes){
            if (processInfo.pid == pid){
                return processInfo.processName;
            }
        }
        return null;
    }

    /**
     * �رո�����
     * @param closeable
     */
    public static void close(Closeable closeable){
        if (closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ����һ���߳�
     * @param runnable
     */
    public static void executeInThread(Runnable runnable){
        new Thread(runnable).start();
    }
}
