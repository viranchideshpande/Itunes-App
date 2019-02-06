package com.example.nirmalsella.itunesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nirmal Sella on 23-10-2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    static final String dbName = "myapps.db";
    static final int dbVersion = 1;

    public DatabaseOpenHelper(Context context)
    {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        AppsTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        AppsTable.onUpdate(db,oldVersion,newVersion);
    }
}
