package com.lixue.aibei.chapter2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/11/24.
 */
public class Book implements Parcelable {
    public  int bookId;
    public String bookName;

    public Book(int bookId,String bookName){
        this.bookId = bookId;
        this.bookName = bookName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bookId);
        parcel.writeString(bookName);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>(){

        @Override
        public Book createFromParcel(Parcel parcel) {
            return new Book(parcel);
        }

        @Override
        public Book[] newArray(int i) {
            return new Book[i];
        }
    };

    private Book(Parcel parcel){
        bookName = parcel.readString();
        bookId = parcel.readInt();
    }
}
