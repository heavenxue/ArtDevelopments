package com.lixue.aibei.chapter2.binderpool;

import android.os.IBinder;

/**
 * Created by Administrator on 2015/11/17.
 */
interface IBinderPool {
    IBinder queryBinder(int binderCode);
}
