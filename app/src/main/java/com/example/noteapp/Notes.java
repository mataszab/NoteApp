package com.example.noteapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Notes implements Parcelable {
    String Name;
    String Desc;

    public Notes(String name, String desc) {
        Name = name;
        Desc = desc;
    }

    protected Notes(Parcel in) {
        Name = in.readString();
        Desc = in.readString();
    }

    public static final Creator<Notes> CREATOR = new Creator<Notes>() {
        @Override
        public Notes createFromParcel(Parcel in) {
            return new Notes(in);
        }

        @Override
        public Notes[] newArray(int size) {
            return new Notes[size];
        }
    };

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeString(Desc);
    }
}
