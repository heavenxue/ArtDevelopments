package com.lixue.aibei.chapter2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.lixue.aibei.chapter2.util.MyConstants;


public class MessengerActivity extends ActionBarActivity {
    private static final String TAG = "MessengerActivity";

    private Messenger mService;

    /**为了接收服务端的回复，客户端也要准备一个接收消息的Messenger和Handler**/
    private Messenger mMessenger = new Messenger(new MessengerHandler());

    private class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyConstants.MSG_FROM_SERVICE:
                    Log.i(TAG, msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
            super.handleMessage(msg);
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = new Messenger(iBinder);
            Message msg = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg","hello,this is client");
            msg.setData(data);
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //接收服务端的消息
            msg.replyTo = mMessenger;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        //绑定另外一个service,MessengerService
        Intent intent = new Intent(this,MessengerService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
//        unbindService(connection);
        super.onDestroy();
    }
}
