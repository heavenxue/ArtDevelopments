package com.lixue.aibei.chapter2;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.lixue.aibei.chapter2.util.MyConstants;

public class MessengerService extends Service {
    private static final String NAME = "MessengerService";

    private class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MyConstants.MSG_FROM_CLIENT:
                    Log.i(NAME,"receive msg from Client:"+msg.getData().get("msg"));
                    Messenger client = msg.replyTo;
                    Message replyMsg = Message.obtain(null,MyConstants.MSG_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","嗯，你的消息我已经收到，稍后会回复你");
                    replyMsg.setData(bundle);
                    try {
                        client.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
            super.handleMessage(msg);
        }
    }
    private final Messenger mMessenger = new Messenger(new MessengerHandler());
    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
