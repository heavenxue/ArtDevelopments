// IBookManager.aidl
package com.lixue.aibei.chapter2;
import com.lixue.aibei.chapter2.Book;

// Declare any non-default types here with import statements

interface IBookManager {
   List<Book> getBookList();
   void addBook(in Book book);
}
