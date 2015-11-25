// IOnNewBookArrivedListener.aidl
package com.lixue.aibei.chapter2;
import com.lixue.aibei.chapter2.Book;

// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
    void onNewBookArrivedlistener(in Book newBook);
}
