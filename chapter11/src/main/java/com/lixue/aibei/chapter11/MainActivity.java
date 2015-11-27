package com.lixue.aibei.chapter11;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runAsyncTask();
        runIntentService();
        runThreadPoolExecutor();
    }

    /**AsyncTask 是一种轻量级的异步任务类
     * 并不适合太耗时的后台任务，对于特别耗时的任务来说，建议使用线程池
     * 原理：
     * AsyncTask中有两个线程池（SerialExecutor和THREAD_POOL_EXECUTOR）和一个Handler(IntentHandler)
     * 其中线程池SerialExecutor用于任务的排队，而线程池THREAD_POOL_EXECUTOR用于真正任务的执行，IntentHandler用于
     * 将执行环境从线程池切换到主线程
     * **/
    private void runAsyncTask(){
        try {
            new DownloadFilesTask().execute(new URL("http://www.baidu.com"),new URL("http://www.renyugang.cn"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private class DownloadFilesTask extends AsyncTask<URL,Integer,String>{
        @Override
        protected void onPreExecute() {
            /**运行在主线程中**/
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            /**运行在子线程中**/
            int count =  urls.length;
            String result = "";
            if (count > 0){
                for (int i = 0;i < count;i++){
                    // totalSize += Downloaderwnloader.downloadFile(urls[i]);
                    result += Downloader.downloadFile(urls[i]) +"笑笑分割线";
                    publishProgress((int) ((i / (float) count) * 100));//用来更新下载的进度，onProgressUpdate才会被调用
                    // Escape early if cancel() is called
                    if (isCancelled())
                        break;
                }
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            /**运行在主线程中**/
//            setProgressPercent(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            /**运行在主线程中**/
            Toast.makeText(MainActivity.this,"Downloaded " + result + "bytes",Toast.LENGTH_SHORT).show();
        }
    }
    private void runIntentService(){
        Intent intent = new Intent(this,LocalIntentService.class);
        intent.putExtra("task_action","com.lixue.aibei.chapter11.TASK1");
        startService(intent);
        Intent intent1 = new Intent(this,LocalIntentService.class);
        intent1.putExtra("task_action","com.lixue.aibei.chapter11.TASK2");
        startService(intent1);
        Intent intent2 = new Intent(this,LocalIntentService.class);
        intent2.putExtra("task_action","com.lixue.aibei.chapter11.TASK3");
        startService(intent2);
    }
    private void runThreadPoolExecutor(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
            }
        };
        /**只有核心线程，且有固定的数量，永远不会被回收，除非线程池被关闭**/
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        fixedThreadPool.execute(runnable);
        /**只有非核心线程，数量必定，最大线程数可以任意大**/
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(runnable);
        /**核心线程数量固定，非核心线程数量没有限制**/
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
        // 2000ms后执行command
        scheduledThreadPool.schedule(runnable, 2000, TimeUnit.MILLISECONDS);
        // 延迟10ms后，每隔1000ms执行一次command
        scheduledThreadPool.scheduleAtFixedRate(runnable, 10, 1000, TimeUnit.MILLISECONDS);
        /**只有一个核心线程，确保所有的任务都在同一个线程中按顺序执行，任务间不需要处理线程的同步问题**/
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(runnable);
    }
}
