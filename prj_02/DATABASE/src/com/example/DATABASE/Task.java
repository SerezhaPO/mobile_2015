package com.example.DATABASE;

import android.os.Parcel;
import android.os.Parcelable;


public class Task implements Parcelable {
    int id;
    String text;
    boolean done;

    Task(int id, String _text, boolean _done) {
        this.id = id;
        text = _text;
        done = _done;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Integer.toString(id));
        dest.writeString(text);
        dest.writeString(Boolean.toString(done));
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public Task(Parcel in) {
        id = Integer.parseInt(in.readString());
        text = in.readString();
        done = Boolean.getBoolean(in.readString());
    }
}
