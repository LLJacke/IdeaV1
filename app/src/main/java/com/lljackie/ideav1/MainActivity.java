package com.lljackie.ideav1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Runnable, AdapterView.OnItemClickListener {

    private Button bt_add;
    private ListView lv_idea;
    private MyDBHelper mDatabase;
    private SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_add = (Button) findViewById(R.id.bt_add);
        lv_idea = (ListView) findViewById(R.id.lv_idea);
        mDatabase = new MyDBHelper(this, "IdeaDB.db", null, 1);
        mDatabase.close();

        new Thread(this).start();

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AddAct.class);
                startActivity(intent);
            }
        });

        lv_idea.setOnItemClickListener(this);

    }

    private void updateList() {
        adapter = new SimpleAdapter(this, getData(), R.layout.idealist,
                new String[]{"id", "title", "des"},
                new int[]{R.id.list_id, R.id.list_title, R.id.list_des});
        lv_idea.setAdapter(adapter);
    }

    private List<Map<String, String>> getData() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = mDatabase.getReadableDatabase();

        Map<String, String> map;
        Cursor cursor = null;

        try {
            cursor = db.query("IdeaDB", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                map = new HashMap<String, String>();
                map.put("id", cursor.getString(0));
                map.put("title", cursor.getString(1));
                map.put("des", cursor.getString(2));
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        db.close();

        return list;
    }

    @Override
    public void run() {
        mDatabase = new MyDBHelper(this, "IdeaDB.db", null, 2);
        updateList();
        mDatabase.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String, String> map = (HashMap<String, String>) lv_idea.getItemAtPosition(position);
        int clickId = Integer.parseInt(map.get("id"));

        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ShowAct.class);
        intent.putExtra("clickid", clickId);
        startActivity(intent);
    }
}
