package com.lixue.aibei.chapter11;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载类
 * Created by Administrator on 2015/11/27.
 */
public class Downloader {
    private static final String TAG = "Downloader";

    public static String downloadFile(URL url){

        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
             connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,"url打开失败，请检查url的正确性....");
        }
        if (connection != null){
            int code = 0;
            try {
                code = connection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader bufferedReader = null;
            if (code == HttpURLConnection.HTTP_OK){
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    if (bufferedReader != null){
                        byte[] read = new byte[1024];
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null){
                            stringBuilder.append(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return stringBuilder.toString();
    }
}
