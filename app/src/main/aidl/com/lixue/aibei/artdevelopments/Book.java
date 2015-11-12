package com.lixue.aibei.artdevelopments;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/11/12.
 */
public class Book implements Parcelable {
    private int bookId;
    private String bookName;

    public Book(Parcel parcel){
        this.bookId = parcel.readInt();
        this.bookName = parcel.readString();
    }

    public Book(int bid,String bname){
        this.bookId = bid;
        this.bookName = bname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bookName);
        parcel.writeInt(bookId);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel parcel) {
            return new Book(parcel);
        }

        @Override
        public Book[] newArray(int i) {
            return new Book[i];
        }
    };

}
