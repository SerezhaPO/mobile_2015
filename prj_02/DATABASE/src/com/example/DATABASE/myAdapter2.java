package com.example.DATABASE;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class myAdapter2 extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<List> list;

    @Override
    public int getCount() {
        return list.size();
    }

    DatabaseHelper db;

    myAdapter2(Context context) {

        this.context = context;
        db = new DatabaseHelper(context);
        this.list = new ArrayList<List>();
        Cursor cursor = db.getListsCursor();
        while (cursor.moveToNext()) {
            list.add(new List(cursor.getInt(0), cursor.getString(1)));

        }
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public List getItem(int position) {
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
            view = inflater.inflate(R.layout.listviewlayout2, parent, false);
        }
        List list = getItem(position);
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(list.text);
        return view;
    }


}
