package com.lixue.aibei.chapter2;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * BookManager 應用的service  服務端的處理過程
 */
public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";
//    private List<Book> mBooklist = new ArrayList<Book>();
    /**
     * CopyOnWriteArrayList支持并发读写
     */
    private CopyOnWriteArrayList<Book> mBooklist = new CopyOnWriteArrayList<Book>();

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);//服務是否銷毀
    /**RemoteCallbackList是系统专门提供的用于删除跨进程listener的接口**/
    /**RemoteCallbackList还有一个很有用的功能，那就是客户端进程终止后，它能够自动移除客户端注册的listener**/
    private RemoteCallbackList<IOnNewBookArrivedListener> mListener = new RemoteCallbackList<>();

    public BookManagerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBooklist.add(new Book(1, "Android"));
        mBooklist.add(new Book(2, "Ios"));
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        /**添加权限验证**/
        int check = checkCallingOrSelfPermission("com.lixue.aibei.chapter2.permission.ACCESS_BOOK_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED){
            return null;
        }

        return mBinder;
    }

    private final IBookManager.Stub mBinder = new IBookManager.Stub(){

        @Override
        public List<Book> getBookList() throws RemoteException {
            synchronized (mBooklist){
                return mBooklist;
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            synchronized (mBooklist){
                if (!mBooklist.contains(book)){
                    mBooklist.add(book);
                }
            }
        }

        @Override
        public void registListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mListener.register(listener);

            final int N = mListener.beginBroadcast();
            mListener.finishBroadcast();
            Log.d(TAG, "registerListener, current size:" + N);
        }

        @Override
        public void unregistListener(IOnNewBookArrivedListener listener) throws RemoteException {
            boolean success = mListener.unregister(listener);

            if (success) {
                Log.d(TAG, "unregister success.");
            } else {
                Log.d(TAG, "not found, can not unregister.");
            }
            final int N = mListener.beginBroadcast();
            mListener.finishBroadcast();
            Log.d(TAG, "unregisterListener, current size:" + N);
        }
    };

    private class ServiceWorker implements Runnable{

        @Override
        public void run() {
            // do background processing here.....
            while (!mIsServiceDestoryed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBooklist.size() + 1;
                Book newBook = new Book(bookId, "new book#" + bookId);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void onNewBookArrived(Book book) throws RemoteException {
        mBooklist.add(book);
        for (int i=0;i<mListener.beginBroadcast();i++){
            IOnNewBookArrivedListener listener = mListener.getBroadcastItem(i);
            Log.d(TAG,"onNewBookArrived,notify listener:"+ listener);
            listener.onNewBookArrivedlistener(book);
        }
    }
}
