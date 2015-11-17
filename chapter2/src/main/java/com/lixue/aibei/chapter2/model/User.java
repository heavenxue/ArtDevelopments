package com.lixue.aibei.chapter2.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 用户基本类
 * Created by Administrator on 2015/11/17.
 */
public class User implements Serializable,Parcelable {
    private static final long serialVersionUID = 519067123721295773L;

    public int userId;
    public String userName;
    public boolean isMale;
//    public Book book;

    public User(){

    }

    public User(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeString(userName);
        parcel.writeInt(isMale ? 1 : 0);
//        parcel.writeParcelable(book,0);
    }
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readInt() == 1;
//        book = in.readParcelable(Thread.currentThread().getContextClassLoader());
    }

    @Override
    public String toString() {
        return String.format("User:{userId:%s, userName:%s, isMale:%s}, with child:{%s}",
                userId, userName, isMale, "");
    }

}
