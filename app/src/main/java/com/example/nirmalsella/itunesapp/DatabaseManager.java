package com.example.nirmalsella.itunesapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;

/**
 * Created by Nirmal Sella on 23-10-2017.
 */

public class DatabaseManager {
    private Context context;
    private DatabaseOpenHelper dbOpenHelper;
    private AppsDAO appsDAO;
    private SQLiteDatabase db;

    public DatabaseManager(Context context)
    {
        this.context = context;
        this.dbOpenHelper = new DatabaseOpenHelper(this.context);
        this.db = dbOpenHelper.getWritableDatabase();
        this.appsDAO = new AppsDAO(this.db);
    }

    public void close()
    {
        if(db!=null)
        {
            db.close();
        }
    }

    public AppsDAO getAppsDAO()
    {
        return this.appsDAO;
    }

    public long saveApp(Itunes newApp)
    {
        return this.appsDAO.save(newApp);
    }

    public boolean updateApp(Itunes newApp)
    {
        return this.appsDAO.update(newApp);
    }

    public boolean deleteApp(long id)
    {
        return this.appsDAO.delete(id);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Itunes getNewApp(long _id)
    {
        return this.appsDAO.getNewApp(_id);
    }

    public List<Itunes> getListOfApps()
    {
        return this.appsDAO.getListOfApps();
    }

}