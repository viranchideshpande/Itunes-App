package com.example.nirmalsella.itunesapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nirmal Sella on 23-10-2017.
 */

public class AppsDAO {

    private SQLiteDatabase db;

    public AppsDAO(SQLiteDatabase db)
    {
        this.db = db;
    }

    public long save(Itunes newApp)
    {
        ContentValues values = new ContentValues();
        values.put(AppsTable.COLUMN_NAME, newApp.getTitle());
        values.put(AppsTable.COLUMN_PRICE, newApp.getPrice());
        values.put(AppsTable.COLUMN_URL_SMALL, newApp.getSmallImage());
        values.put(AppsTable.COLUMN_URL_LARGE, newApp.getLargeImage());
        return db.insert(AppsTable.TABLENAME, null, values);
    }

    public boolean update(Itunes newApp)
    {
        ContentValues values = new ContentValues();
        values.put(AppsTable.COLUMN_NAME, newApp.getTitle());
        values.put(AppsTable.COLUMN_PRICE, newApp.getPrice());
        values.put(AppsTable.COLUMN_URL_SMALL, newApp.getSmallImage());
        values.put(AppsTable.COLUMN_URL_LARGE, newApp.getLargeImage());
        return db.update(AppsTable.TABLENAME, values, AppsTable.COLUMN_ID + "=?", new String[]{newApp.get_id()+""}) > 0;
    }

    public boolean delete(long id)
    {
        return db.delete(AppsTable.TABLENAME, AppsTable.COLUMN_ID + "=?", new String[]{id+""}) > 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Itunes getNewApp(long _id)
    {
        Itunes newApp = null;
        Cursor c = db.query(true,AppsTable.TABLENAME,new String[]{AppsTable.COLUMN_ID,AppsTable.COLUMN_NAME,
                AppsTable.COLUMN_PRICE,AppsTable.COLUMN_URL_SMALL,AppsTable.COLUMN_URL_LARGE},
                AppsTable.COLUMN_ID + "=?",new String[]{_id+""},null,null,null,null,null);
        if (c!=null && c.moveToFirst())
        {
            newApp = buildAppFromCursor(c);
            if(!c.isClosed())
            {
                c.close();
            }
        }
        return newApp;
    }

    public List<Itunes> getListOfApps()
    {
        List<Itunes> apps = new ArrayList<Itunes>();
        Cursor c = db.query(AppsTable.TABLENAME,new String[]{AppsTable.COLUMN_ID,AppsTable.COLUMN_NAME,AppsTable.COLUMN_PRICE,AppsTable.COLUMN_URL_SMALL,AppsTable.COLUMN_URL_LARGE},null,null,null,null,null);
        if (c!=null && c.moveToFirst())
        {
            do {
                Itunes newApp = new Itunes();
                if (newApp!=null)
                {
                    newApp = buildAppFromCursor(c);
                }
                apps.add(newApp);
            }while(c.moveToNext());

            if(!c.isClosed())
            {
                c.close();
            }
        }
        return apps;
    }

    private Itunes buildAppFromCursor(Cursor c){
        Itunes newApp = null;
        if (c!=null)
        {
            newApp = new Itunes();
            newApp.set_id(c.getLong(0));
            newApp.setTitle(c.getString(1));
            newApp.setPrice(c.getDouble(2));
            newApp.setSmallImage(c.getString(3));
            newApp.setLargeImage(c.getString(4));
        }
        return newApp;
    }
}
