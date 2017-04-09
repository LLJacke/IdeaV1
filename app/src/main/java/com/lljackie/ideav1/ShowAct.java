package com.lljackie.ideav1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowAct extends AppCompatActivity {

    private Button bt_back;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    MyDBHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        bt_back = (Button) findViewById(R.id.bt_back);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);

        Intent intent = getIntent();
        int getId = intent.getIntExtra("clickid", 0);
        show(getId);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ShowAct.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void show(int getId){
        mDatabase = new MyDBHelper(this, "IdeaDB.db", null, 2);
        SQLiteDatabase db = mDatabase.getWritableDatabase();
        Cursor cursor = db.query("IdeaDB", new String[]{"title", "des", "other"}, "id = " + getId, null, null, null, null);
        if (cursor.moveToFirst()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String des = cursor.getString(cursor.getColumnIndex("des"));
            String other = cursor.getString(cursor.getColumnIndex("other"));

            tv_1.setText(title);
            tv_2.setText(des);
            tv_3.setText(other);
        }
        cursor.close();
        db.close();
        mDatabase.close();
    }
}
