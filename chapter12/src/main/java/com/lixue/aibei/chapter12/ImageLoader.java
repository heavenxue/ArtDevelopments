package com.lixue.aibei.chapter12;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Administrator on 2015/11/30.
 */
public class ImageLoader {
    private static final String TAG = "ImageLoader";
    private static final int MESSAGE_POST_RESULT = 1;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();//cpu总数
    private static final int CORE_POOL_SIZE = CPU_COUNT +1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10l;
    private static final int TAG_KEY_URI = R.id.imageloader_uri;
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;//最大磁盘缓存空间
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final int DISK_CACHE_INDEX =0;
    private boolean mIsDiskLruCacheCreated = false; //是否缓存到磁盘

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable,"ImageLoader#" + mCount.getAndIncrement());
        }
    };
    //声明了一个线程池
    public static final Executor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,
            KEEP_ALIVE, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(),sThreadFactory);

    private Handler mMainHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            LoaderResult result = (LoaderResult) msg.obj;
            ImageView imageView = result.imageView;
            imageView.setImageBitmap(result.bitmap);
            String uri = (String) imageView.getTag(TAG_KEY_URI);
            if (uri.equals(result.Uri)){
                imageView.setImageBitmap(result.bitmap);
            }else{
                Log.w(TAG,"set image bitmap,but url has changed,ignored!");
            }
            super.handleMessage(msg);
        }
    };

    private Context mContext;
    private ImageResizer imageResizer;
    private LruCache<String,Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;

    private ImageLoader(Context context){
        mContext = context.getApplicationContext();
        //最大内存空间
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;//以最大空间的1/8作为内存的最大缓存
        mMemoryCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

        File disCacheDir = getDiskCacheDir(mContext,"bitmap");
        if (!disCacheDir.exists()){
            disCacheDir.mkdirs();
        }
        if (getUsableSpace(disCacheDir) > DISK_CACHE_SIZE){
            try{
                mDiskLruCache = DiskLruCache.open(disCacheDir,1,1,DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    /**
     * 构造一个ImageLoader实例
     * @param context
     * @return
     */
    public static  ImageLoader build(Context context){
        return new ImageLoader(context);
    }

    /**
     * 把图片加到内存缓存中
     */
    private void addBitmapToMemoryCache(String key,Bitmap bitmap){
        if (getBitmapFromMemoryCache(key) == null){
            mMemoryCache.put(key,bitmap);
        }
    }
    private Bitmap getBitmapFromMemoryCache(String key){
        return mMemoryCache.get(key);
    }

    /**
     * 从内存，磁盘，或网络获取图片显示到imageview上
     * @param uri
     * @param imageView
     */
    public void bindBitmap(final String uri,final ImageView imageView){
        bindBitmap(uri, imageView, 0, 0);
    }

    public void bindBitmap(final String uri,final ImageView imageView,final int reqWidth, final int reqHeight){
        imageView.setTag(TAG_KEY_URI, uri);
        final Bitmap bitmap = LoadBitmapFromMemCache(uri);
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
            return;
        }
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap1 = LoadBitmap(uri,reqWidth,reqHeight);
                if (bitmap1!= null){
                    LoaderResult result = new LoaderResult(imageView,uri,bitmap1);
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT,result).sendToTarget();
                }
            }
        };
        THREAD_POOL_EXECUTOR.execute(runnable);
    }

    public Bitmap LoadBitmap(String uri,int reqWidth,int reqHeight){
        Bitmap bitmap = LoadBitmapFromMemCache(uri);
        if (bitmap != null){
            Log.d(TAG,"LoadBitmpaFromMemCache,url:"+uri);
            return bitmap;
        }
        bitmap = LoadBitmapFromDiskCache(uri,reqWidth,reqHeight);
        if (bitmap !=null){
            Log.d(TAG,"loadBitmapFromDisk,url:" + uri);
            return bitmap;
        }
        try {
            bitmap = LoadBitmapFromHttp(uri,reqWidth,reqHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "loadBitmapFromHttp,url:" + uri);
        if (bitmap != null && !mIsDiskLruCacheCreated){
            Log.w(TAG,"encounter error,DiskLruCache is not created.");
            bitmap = downloadBitmapFromUrl(uri);
        }
        return bitmap;
    }

    /**
     * 从网络获取的图片，魂村到磁盘，内存中，最后再从磁盘中获取自己缓存的图片
     * @param url
     * @param reqWidth
     * @param reqHeight
     * @return
     * @throws IOException
     */
    private Bitmap LoadBitmapFromHttp(String url,int reqWidth,int reqHeight) throws IOException {
        if (Looper.myLooper() == Looper.getMainLooper()){
            throw  new RuntimeException("can not visit network form UI Thread");
        }
        if (mDiskLruCache == null){
            return null;
        }
        String key = hashKeyFromUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null){
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if(dowloadUrlToStream(url, outputStream)){
                editor.commit();
            }else{
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return LoadBitmapFromDiskCache(url, reqWidth, reqHeight);
    }

    private boolean dowloadUrlToStream(String urlStr,OutputStream outputStream){
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            final URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
            out = new BufferedOutputStream(outputStream,IO_BUFFER_SIZE);
            int b;
            while ((b = in.read()) != -1){
                out.write(b);
            }
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG,"downloadBitmap failed. " + e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "downloadBitmap failed. " + e);
        }finally {
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
            MyUtils.close(out);
            MyUtils.close(in);
        }
        return false;
    }

    private Bitmap downloadBitmapFromUrl(String url){
        HttpURLConnection connection = null;
        BufferedInputStream in = null;
        Bitmap bitmap = null;

        try {
            URL mUrl = new URL(url);
            connection = (HttpURLConnection) mUrl.openConnection();
            in = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(in);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG,"Error in downloadBitmpa:" + e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,"Error in downloadBitmpa:" + e);
        }finally {
            if (connection != null){
                connection.disconnect();
            }
            MyUtils.close(in);
        }
        return bitmap;
    }

    private Bitmap LoadBitmapFromDiskCache(String url,int reqWidth,int reqHeight){
        String key = hashKeyFromUrl(url);
        Bitmap bitmap = null;
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot !=null){
                FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(DISK_CACHE_INDEX);
                FileDescriptor fileDescriptor = fileInputStream.getFD();
                bitmap = ImageResizer.decodeSampleBitmapFromFileDescriptro(fileDescriptor,reqWidth,reqHeight);
                if (bitmap != null){
                    addBitmapToMemoryCache(key,bitmap);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private Bitmap LoadBitmapFromMemCache(String uri){
        String key = hashKeyFromUrl(uri);
        return getBitmapFromMemoryCache(key);
    }

    //将url转换为key,以md5加密
    private String hashKeyFromUrl(String uri){
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(uri.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            cacheKey = String.valueOf(uri.hashCode());
        }
        return cacheKey;
    }

    //将字节转换为十六进制
    private String bytesToHexString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for (int i= 0;i < bytes.length;i++){
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1){
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    //得到缓存文件的可用空间
    private long getUsableSpace(File caheFile){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            return caheFile.getUsableSpace();
        }
        final StatFs statFs = new StatFs(caheFile.getPath());
        return (long) statFs.getBlockSize() *(long) statFs.getAvailableBlocks();
    }

    //获取缓存目录
    private File getDiskCacheDir(Context context,String uniqueName){
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        String cachePath;
        if (externalStorageAvailable){
            cachePath = context.getExternalCacheDir().getPath();
        }else{
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
