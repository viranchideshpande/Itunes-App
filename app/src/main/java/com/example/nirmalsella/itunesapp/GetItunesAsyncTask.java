package com.example.nirmalsella.itunesapp;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import static com.example.nirmalsella.itunesapp.MainActivity.filteredList;
import static com.example.nirmalsella.itunesapp.MainActivity.tunesArrayList;

/**
 * Created by Nirmal Sella on 23-10-2017.
 */

public class GetItunesAsyncTask extends AsyncTask<String, Void, ArrayList<Itunes>> {


    MainActivity a1;
    // ArrayList<Movie> musicFavList;

    public GetItunesAsyncTask(MainActivity a1) {
        this.a1 = a1;
        //this.musicFavList = musicFavList;
    }

    @Override
    protected ArrayList<Itunes> doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();

                while(line != null){
                    sb.append(line);
                    line = reader.readLine();
                }


                return ItunesUtil.TunesJSONParser.parseTunes(sb.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

    /*static public interface Idata{
        public static void setProgress(ArrayList<Itunes> tunes);
    }*/

    @Override
    protected void onPostExecute(ArrayList<Itunes> tunes) {
        super.onPostExecute(tunes);
        //a1.setProgress(tunes);
        RecyclerView mRecyclerView = (RecyclerView) a1.findViewById(R.id.my_recycler_view);

        a1.progress.dismiss();
        if(tunes != null){
            Log.d("demo", "MUSIC " + tunes.toString());
        }else{
            Log.d("demo", "NO ARRAYLIST");
        }

        a1.tunesArrayList = new ArrayList();
        HashSet<Itunes> filteredSet = new HashSet<>();
        if(a1.filteredList != null)
            for(Itunes itune: a1.filteredList){
                filteredSet.add(itune);
            }
        for(Itunes itune: tunes){
            if(!filteredSet.contains(itune))
                a1.tunesArrayList.add(itune);
        }

        if(a1.sortSwitch.isChecked()){
            if(tunesArrayList != null) {
                tunesArrayList.sort((a, b) -> (int) (Double.valueOf(a.getPrice()) - Double.valueOf(b.getPrice())));
            }
            if(filteredList != null) {
                filteredList.sort((a, b) -> (int) (Double.valueOf(a.getPrice()) - Double.valueOf(b.getPrice())));
                mRecyclerView.setAdapter(a1.mAdapter);
                a1.mAdapter.notifyDataSetChanged();
            }
        }else if(!(a1.sortSwitch.isChecked())){
            if(tunesArrayList != null) {
                tunesArrayList.sort((a, b) -> (int) (Double.valueOf(b.getPrice()) - Double.valueOf(a.getPrice())));

            }
            if(filteredList != null) {
                filteredList.sort((a, b) -> (int) (Double.valueOf(b.getPrice()) - Double.valueOf(a.getPrice())));
                mRecyclerView.setAdapter(a1.mAdapter);
                a1.mAdapter.notifyDataSetChanged();
            }
        }


        a1.adapter = new TunesAdapter(a1,R.layout.tunes,a1.tunesArrayList);

        a1.listView.setAdapter(a1.adapter);
        a1.adapter.setNotifyOnChange(true);


        a1.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Itunes tunes = new Itunes();
                tunes = (Itunes) adapterView.getItemAtPosition(i);

                a1.dm.saveApp(tunes);
                a1.adapter.remove(tunes);
                filteredList = (ArrayList<Itunes>) a1.dm.getListOfApps();
                if(filteredList != null){
                    a1.fText.setVisibility(View.INVISIBLE);
                }
                a1.mAdapter = new Filtered(filteredList,tunesArrayList);

                if(a1.sortSwitch.isChecked()){
                    if(tunesArrayList != null) {
                        tunesArrayList.sort((a, b) -> (int) (Double.valueOf(a.getPrice()) - Double.valueOf(b.getPrice())));
                    }
                    if(filteredList != null) {
                        filteredList.sort((a, b) -> (int) (Double.valueOf(a.getPrice()) - Double.valueOf(b.getPrice())));
                        mRecyclerView.setAdapter(a1.mAdapter);
                        a1.mAdapter.notifyDataSetChanged();
                    }
                }else if(!(a1.sortSwitch.isChecked())){
                    if(tunesArrayList != null) {
                        tunesArrayList.sort((a, b) -> (int) (Double.valueOf(b.getPrice()) - Double.valueOf(a.getPrice())));

                    }
                    if(filteredList != null) {
                        filteredList.sort((a, b) -> (int) (Double.valueOf(b.getPrice()) - Double.valueOf(a.getPrice())));
                        mRecyclerView.setAdapter(a1.mAdapter);
                        a1.mAdapter.notifyDataSetChanged();
                    }
                }

                mRecyclerView.setAdapter(a1.mAdapter);
                a1.mAdapter.notifyDataSetChanged();

                //Toast.makeText(MainActivity.this, "Clicked on" + tunes.getTitle(), Toast.LENGTH_SHORT).show();
                Log.d("demo","added: " + filteredList.size());

                return false;
            }
        });

//        Log.d("demo","SAAVU"+tunes.toString());
    }


}
