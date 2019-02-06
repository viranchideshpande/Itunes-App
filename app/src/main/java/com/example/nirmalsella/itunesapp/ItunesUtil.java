package com.example.nirmalsella.itunesapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nirmal Sella on 23-10-2017.
 */

public class ItunesUtil {

    static public class TunesJSONParser {
        static ArrayList<Itunes> parseTunes(String in) throws JSONException {
            ArrayList<Itunes> itunesList = new ArrayList<Itunes>();

            JSONObject root = new JSONObject(in);
            JSONObject secObj = root.getJSONObject("feed");
            JSONArray entryArray = secObj.getJSONArray("entry");

            for(int i=0;i<entryArray.length();i++){
                Itunes itunes = new Itunes();
                JSONObject inObj = entryArray.getJSONObject(i);

                JSONObject titleObj = inObj.getJSONObject("im:name");
                itunes.setTitle(titleObj.getString("label"));

                JSONObject priceObj = inObj.getJSONObject("im:price");
                JSONObject price = priceObj.getJSONObject("attributes");
                itunes.setPrice(price.getDouble("amount"));

                JSONArray imArray = inObj.getJSONArray("im:image");

                for(int j = 0; j < imArray.length(); j++){
                    JSONObject imObj = imArray.getJSONObject(j);
                    JSONObject size = imObj.getJSONObject("attributes");

                    if(size.getString("height").equals("53")){
                        itunes.setSmallImage(imObj.getString("label"));
                        //Log.d("demo", "smalll" );
                    }else if(size.getString("height").equals("100")){
                        itunes.setLargeImage(imObj.getString("label"));
                        //Log.d("demo", "largee" );
                    }
                }


                itunesList.add(itunes);
            }


            return itunesList;
        }
    }
}