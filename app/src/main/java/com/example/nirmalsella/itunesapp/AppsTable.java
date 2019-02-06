package com.example.nirmalsella.itunesapp;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nirmal Sella on 23-10-2017.
 */

public class AppsTable {

    static final String TABLENAME = "Filter";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_PRICE = "price";
    static final String COLUMN_URL_SMALL = "small_url";
    static final String COLUMN_URL_LARGE = "thumb_url";

    static public void onCreate(SQLiteDatabase db)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Create Table "+ TABLENAME + " (");
        sb.append(COLUMN_ID + " integer primary key autoincrement, ");
        sb.append(COLUMN_NAME + " text not null, ");
        sb.append(COLUMN_PRICE + " real not null, ");
        sb.append(COLUMN_URL_SMALL + " text, ");
        sb.append(COLUMN_URL_LARGE + " text);");
        try{
            db.execSQL(sb.toString());
        }catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    static public void onUpdate(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("Drop TABLE IF EXISTS " + TABLENAME);
        AppsTable.onCreate(db);
    }
}
