package com.lljackie.ideav1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddAct extends AppCompatActivity implements View.OnClickListener {

    EditText et_1;
    EditText et_2;
    EditText et_3;
    Button bt_upload;
    MyDBHelper mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        et_1 = (EditText) findViewById(R.id.et_1);
        et_2 = (EditText) findViewById(R.id.et_2);
        et_3 = (EditText) findViewById(R.id.et_3);
        bt_upload = (Button) findViewById(R.id.bt_upload);



        bt_upload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String title, des, other;
        mDatabase = new MyDBHelper(this, "IdeaDB.db", null, 2);

        title = et_1.getText().toString();
        des = et_2.getText().toString();
        other = et_3.getText().toString();

        if (!title.isEmpty() && !des.isEmpty()) {
            //upload the info
            SQLiteDatabase db = mDatabase.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put("title", title);
            cv.put("des", des);
            cv.put("other", other);
            db.insert("IdeaDB", null, cv);
            db.close();
            mDatabase.close();

            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "title or describe is empty", Toast.LENGTH_SHORT).show();
        }
    }
}
