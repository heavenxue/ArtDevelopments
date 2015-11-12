package com.lixue.aibei.artdevelopments;

import com.lixue.aibei.artdevelopments.chapter_2.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
