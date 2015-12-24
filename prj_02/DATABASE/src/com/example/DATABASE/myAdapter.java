package com.example.DATABASE;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class myAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<Task> list;
    String listname;

    @Override
    public int getCount() {
        return list.size();
    }

    DatabaseHelper db;

    myAdapter(Context context, String listname) {
        this.context = context;
        db = new DatabaseHelper(context);
        this.list = new ArrayList<Task>();
        this.listname = listname;
        Cursor cursor = db.getReadableDatabase().rawQuery("SELECT * FROM " + DatabaseHelper.Table.TABLE_NAME + " WHERE " + DatabaseHelper.Table.COLUMN_FROMLIST + "=\"" + listname+"\"", null);
        while (cursor.moveToNext()) {
            list.add(new Task(cursor.getInt(0), cursor.getString(1), cursor.getInt(2) == 1));

        }
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Task getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.listviewlayout, parent, false);
        }
        final Task task = getItem(position);
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(task.text);
        final CheckBox box = (CheckBox) view.findViewById(R.id.checkbox);
        box.setChecked(task.done);
        box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (box.isChecked()) {
                    task.done = true;
                } else {
                    task.done = false;
                }
                db.setDone(task);
                Cursor cursor = db.getReadableDatabase().rawQuery("select * from " + DatabaseHelper.Table.TABLE_NAME + " where " + DatabaseHelper.Table.COLUMN_ID + "=" + task.id, null);
                cursor.moveToFirst();
                String c = cursor.getString(1) + " - ";
                if (cursor.getInt(2) == 1) {
                    c += "Сделано!";
                } else {
                    c += "Не сделано!";
                }
                Toast toast = Toast.makeText(context.getApplicationContext(), c, Toast.LENGTH_LONG);
                toast.show();
            }
        });
        return view;
    }


}
