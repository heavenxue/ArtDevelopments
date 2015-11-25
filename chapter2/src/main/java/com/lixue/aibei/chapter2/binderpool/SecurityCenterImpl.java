package com.lixue.aibei.chapter2.binderpool;

import android.os.RemoteException;

import com.lixue.aibei.chapter2.ISecurityCenter;

/**
 * Created by Administrator on 2015/11/25.
 */
public class SecurityCenterImpl extends ISecurityCenter.Stub {
    private static final char SECREC_CODE = '^';//异或运算

    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0;i <chars.length;i++){
            chars[i] ^= SECREC_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
