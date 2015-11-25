package com.lixue.aibei.chapter2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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

    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListener = new CopyOnWriteArrayList<>();

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
            if (listener != null){
                mListener.add(listener);
            }else{
                Log.i(TAG,"already exist listener");
            }
            Log.d(TAG,"registListene,size:"+ mListener.size());
        }

        @Override
        public void unregistListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if (mListener.contains(listener)){
                mListener.remove(listener);
                Log.d(TAG,"unregistListener listener succeed.");
            }else{
                Log.d(TAG,"not found,can not unregistListener");
            }
            Log.d(TAG,"unregistListener,size:"+ mListener.size());
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
        for (int i=0;i<mListener.size();i++){
            IOnNewBookArrivedListener listener = mListener.get(i);
            Log.d(TAG,"onNewBookArrived,notify listener:"+ listener);
            listener.onNewBookArrivedlistener(book);
        }
    }
}
