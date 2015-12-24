package com.example.DATABASE;

import android.os.Parcel;
import android.os.Parcelable;


public class List implements Parcelable {
    int id;
    String text;

    List(int id, String _text) {
        this.id = id;
        text = _text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Integer.toString(id));
        dest.writeString(text);
    }

    public static final Creator<List> CREATOR = new Creator<List>() {
        public List createFromParcel(Parcel in) {
            return new List(in);
        }

        public List[] newArray(int size) {
            return new List[size];
        }
    };

    public List(Parcel in) {
        id = Integer.parseInt(in.readString());
        text = in.readString();
    }
}
