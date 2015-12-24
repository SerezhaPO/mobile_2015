package com.example.DATABASE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class TasksActivity extends Activity {
    ListView listView;
    DatabaseHelper db;
    myAdapter adapter;
    LinearLayout layout;
    String listname;
    final static int ADDED = 110;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main2);
        layout = (LinearLayout) findViewById(R.id.layout);
        db = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.listview);
        listname = getIntent().getStringExtra("listname");
        adapter = new myAdapter(TasksActivity.this, listname);
        if (adapter.list.size() == 0) {
            listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0));
            layout.setVisibility(View.VISIBLE);
        } else {
            listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0));
            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            layout.setVisibility(View.INVISIBLE);
        }
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = adapter.getItem(position);
                db.getWritableDatabase().delete(DatabaseHelper.Table.TABLE_NAME, DatabaseHelper.Table.COLUMN_ID + "=" + task.id, null);
                Toast toast = Toast.makeText(getApplicationContext(), "Задание \"" + task.text + "\"успешно удалено", Toast.LENGTH_LONG);
                adapter.list.remove(position);
                if (adapter.list.size() == 0) {
                    listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
                    layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0));
                    layout.setVisibility(View.VISIBLE);
                } else {
                    listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0));
                    layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
                    layout.setVisibility(View.INVISIBLE);
                }
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                toast.show();
                return false;
            }
        });
    }

    public void addItem(View view) {
        Intent intent = new Intent(this, AddTaskActivity.class);
        intent.putExtra("listname", listname);
        startActivityForResult(intent, ADDED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            Task task = data.getParcelableExtra("task");
            adapter.list.add(task);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
        } catch (Exception e) {

        }
        if (adapter.list.size() == 0) {
            listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0));
            layout.setVisibility(View.VISIBLE);
        } else {
            listView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0));
            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            layout.setVisibility(View.INVISIBLE);
        }
    }

}
