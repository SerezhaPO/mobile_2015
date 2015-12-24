package com.example.DATABASE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tasks.db";
    public static final int DB_VERSION = 1;

    public Cursor getTasksCursor() {
        return this.getReadableDatabase().query(Table.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getListsCursor() {
        return this.getReadableDatabase().query(List.TABLE_NAME, null, null, null, null, null, null);
    }

//    public Cursor getListsCursor() {
//        return this.getReadableDatabase().query(Lists.TABLE_NAME, null, null, null, null, null, null);
//    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    public final static class Table {
        private Table() {
        }

        public static final String TABLE_NAME = "tasklist";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_DONE = "done";
        public static final String COLUMN_FROMLIST = "list";
        public static final int TASK_DONE = 1;
        public static final int TASK_UNDONE = 0;

    }

    public final static class List {
        private List() {
        }

        public static final String TABLE_NAME = "to_do_list";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_TEXT = "text";
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Table.TABLE_NAME + " (" +
                Table.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Table.COLUMN_TEXT + " TEXT NOT NULL," +
                Table.COLUMN_DONE + " INTEGER NOT NULL, " +
                Table.COLUMN_FROMLIST + " TEXT NOT NULL" +
                ");");
        db.execSQL("CREATE TABLE " + List.TABLE_NAME + " (" +
                List.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                List.COLUMN_TEXT + " TEXT NOT NULL" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertTask(String task_name, String list_name) {
        try {
            ContentValues values = new ContentValues();
            values.put(Table.COLUMN_TEXT, task_name);
            values.put(Table.COLUMN_DONE, Table.TASK_UNDONE);
            values.put(Table.COLUMN_FROMLIST, list_name);
            getWritableDatabase().insert(Table.TABLE_NAME, null, values);
        } catch (Exception e) {
        }
    }

    public void insertList(String list_name) {
        try {
            ContentValues values = new ContentValues();
            values.put(List.COLUMN_TEXT, list_name);
            getWritableDatabase().insert(List.TABLE_NAME, null, values);
        } catch (Exception e) {
        }
    }

    public void setDone(Task task) {
        ContentValues values = new ContentValues();
        values.put(Table.COLUMN_TEXT, task.text);
        if (task.done) {
            values.put(Table.COLUMN_DONE, Table.TASK_DONE);
        } else {
            values.put(Table.COLUMN_DONE, Table.TASK_UNDONE);
        }
        getWritableDatabase().update(Table.TABLE_NAME, values, Table.COLUMN_ID + "=" + task.id, null);

    }
}
