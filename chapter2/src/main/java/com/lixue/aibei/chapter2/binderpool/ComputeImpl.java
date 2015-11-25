package com.lixue.aibei.chapter2.binderpool;

import android.os.RemoteException;

import com.lixue.aibei.chapter2.ICompute;

/**
 * Created by Administrator on 2015/11/25.
 */
public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
