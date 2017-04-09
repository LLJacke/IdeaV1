package com.lljackie.ideav1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by LLJ on 2017/4/9.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    public static final String CREATE_IDEADB = "create table IdeaDB ("
            + "id integer primary key autoincrement, "
            + "title text, "
            + "des text, "
            + "other text)";

    private Context mContext;

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_IDEADB);
        Looper.prepare();
        Toast.makeText(mContext, "create succeeded", Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
