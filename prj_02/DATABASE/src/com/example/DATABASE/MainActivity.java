package com.example.DATABASE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;



public class MainActivity extends Activity {
    ListView listView;
    DatabaseHelper db;
    myAdapter2 adapter;
    LinearLayout layout;
    final static int ADDED = 110;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);
        layout = (LinearLayout) findViewById(R.id.layout);
        db = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.listview);
        adapter = new myAdapter2(MainActivity.this);
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, TasksActivity.class);
                TextView textView = (TextView) view.findViewById(R.id.text);
                intent.putExtra("listname", textView.getText());
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                List list = adapter.getItem(position);
                db.getWritableDatabase().delete(DatabaseHelper.Table.TABLE_NAME, DatabaseHelper.Table.COLUMN_ID + "=" + list.id, null);
                Toast toast = Toast.makeText(getApplicationContext(), "Список \"" + list.text + "\"Успешно удалён!", Toast.LENGTH_LONG);
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
        Intent intent = new Intent(this, AddListActivity.class);
        startActivityForResult(intent, ADDED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            List list = data.getParcelableExtra("list");
            adapter.list.add(list);
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
