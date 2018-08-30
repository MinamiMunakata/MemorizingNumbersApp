package com.minami.project.android.memorizingnumbersapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Minami on 2018/08/24.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "shop_item";
    private static String DB_NAME_ASSET = "farm2table.db";
    private static final int DB_VERSION = 1;

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
