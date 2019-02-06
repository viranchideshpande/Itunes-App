package com.example.nirmalsella.itunesapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    StringBuilder url;
    static  ProgressDialog progress;
    static ArrayList<Itunes> tunesArrayList,filteredList;
    static ListView listView;
    static  DatabaseManager dm;
    static TunesAdapter adapter;
    static ImageView refresh;
    static Switch sortSwitch;
    static TextView fText;

    static private RecyclerView mRecyclerView;
    static Filtered  mAdapter;
    static private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onDestroy() {
        dm.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        filteredList = new ArrayList<Itunes>();
        dm = new DatabaseManager(this);
        sortSwitch = (Switch) findViewById(R.id.sortSwitch);
        refresh = (ImageView) findViewById(R.id.refreshButton);
        fText = (TextView) findViewById(R.id.fText);
        fText.setVisibility(View.INVISIBLE);

        if(filteredList == null || filteredList.isEmpty()){
            fText.setVisibility(View.VISIBLE);
            fText.setText("There is no filtered apps to display");
        }

        if(isConnectedOnline()){
            url = new StringBuilder();
            progress= new ProgressDialog(MainActivity.this);
            progress.setTitle("Loading");
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();
            //https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json
            url.append("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
            Log.d("demo", "URL ---" + url.toString());
            new GetItunesAsyncTask(this).execute(url.toString());
        } else{
            Toast.makeText(MainActivity.this, "Not connected to Internet", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetItunesAsyncTask(MainActivity.this).execute(url.toString());
            }
        });

        sortSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sortSwitch.isChecked()){
                    sortSwitch.setText("Ascending");
                    tunesArrayList.sort((a,b) -> (int) (Double.valueOf(a.getPrice()) - Double.valueOf(b.getPrice())));
                    filteredList.sort((a,b) -> (int) (Double.valueOf(a.getPrice()) - Double.valueOf(b.getPrice())));
                    adapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                    Log.d("demo", "ASCENDING SORT TUNES" + tunesArrayList.toString());
                    Log.d("demo", "ASCENDING SORT FILTERED" + filteredList.toString());
                }else if(!(sortSwitch.isChecked())){
                    sortSwitch.setText(("Descending"));
                    tunesArrayList.sort((a,b) -> (int) (Double.valueOf(b.getPrice()) - Double.valueOf(a.getPrice())));
                    filteredList.sort((a,b) -> (int) (Double.valueOf(b.getPrice()) - Double.valueOf(a.getPrice())));
                    adapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                    Log.d("demo", "descending SORT TUNES" + tunesArrayList.toString());
                    Log.d("demo", "descending SORT FILTERED" + filteredList.toString());
                }
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(false);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new Filtered(filteredList,tunesArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        if(sortSwitch.isChecked()){
            sortSwitch.setText("Ascending");
            if(tunesArrayList != null) {
                tunesArrayList.sort((a, b) -> (int) (Double.valueOf(a.getPrice()) - Double.valueOf(b.getPrice())));
                adapter.notifyDataSetChanged();
            }
            if(filteredList != null) {
                filteredList.sort((a, b) -> (int) (Double.valueOf(a.getPrice()) - Double.valueOf(b.getPrice())));
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }else if(!(sortSwitch.isChecked())){
            sortSwitch.setText(("Descending"));
            if(tunesArrayList != null) {
                tunesArrayList.sort((a, b) -> (int) (Double.valueOf(b.getPrice()) - Double.valueOf(a.getPrice())));
                adapter.notifyDataSetChanged();
            }
            if(filteredList != null) {
                filteredList.sort((a, b) -> (int) (Double.valueOf(b.getPrice()) - Double.valueOf(a.getPrice())));
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private boolean isConnectedOnline(){
        ConnectivityManager cm=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return  false;
    }


    public static void setProgress(ArrayList<Itunes> itunes) {

        /*progress.dismiss();
        if(itunes != null){
            Log.d("demo", "MUSIC " + itunes.toString());
        }else{
            Log.d("demo", "NO ARRAYLIST");
        }

        tunesArrayList = itunes;
        adapter = new TunesAdapter(MainActivity.this,R.layout.tunes,tunesArrayList);*/
        if(sortSwitch.isChecked()){
            if(tunesArrayList != null) {
                tunesArrayList.sort((a, b) -> (int) (Double.valueOf(a.getPrice()) - Double.valueOf(b.getPrice())));
            }
            if(filteredList != null) {
                filteredList.sort((a, b) -> (int) (Double.valueOf(a.getPrice()) - Double.valueOf(b.getPrice())));
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }else if(!(sortSwitch.isChecked())){
            if(tunesArrayList != null) {
                tunesArrayList.sort((a, b) -> (int) (Double.valueOf(b.getPrice()) - Double.valueOf(a.getPrice())));

            }
            if(filteredList != null) {
                filteredList.sort((a, b) -> (int) (Double.valueOf(b.getPrice()) - Double.valueOf(a.getPrice())));
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }

        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        filteredList = (ArrayList<Itunes>) dm.getListOfApps();

        if(filteredList == null || filteredList.isEmpty()){
            fText.setVisibility(View.VISIBLE);
            fText.setText("There is no filtered apps to display");
        }

        mAdapter = new Filtered(filteredList,tunesArrayList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        Log.d("demo","removed " + filteredList.size());
    }
}
