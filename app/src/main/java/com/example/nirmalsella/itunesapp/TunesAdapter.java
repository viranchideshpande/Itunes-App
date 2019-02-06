package com.example.nirmalsella.itunesapp;

/**
 * Created by Nirmal Sella on 23-10-2017.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nirmal Sella on 16-10-2017.
 */

public class TunesAdapter extends ArrayAdapter<Itunes> {

    Context mContext;
    List<Itunes> mData;
    int mResource;
    //FavSharedPref favSharedPref;



    public TunesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Itunes> objects) {
        super(context, resource, objects);

        this.mContext=context;
        this.mResource=resource;
        this.mData=objects;
        //favSharedPref=new FavSharedPref();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(mResource,parent,false);
        }

        final Itunes tunes= mData.get(position);

        ImageView tunesView = (ImageView) convertView.findViewById(R.id.imageView);
        Picasso.with(mContext).load(tunes.getSmallImage()).into(tunesView);

        TextView textViewTitle = (TextView) convertView.findViewById(R.id.textViewTitle);
        textViewTitle.setText(tunes.getTitle());

        TextView textViewPrice = (TextView) convertView.findViewById(R.id.textViewReleaseDate);
        textViewPrice.setText("Price: USD " +String.valueOf(tunes.getPrice()));

        ImageView priceView = (ImageView) convertView.findViewById(R.id.imageView2);
        if(tunes.getPrice() < 2){
            Picasso.with(mContext).load(R.drawable.price_low).into(priceView);
        } else if(tunes.getPrice() < 6){
            Picasso.with(mContext).load(R.drawable.price_medium).into(priceView);
        }else{
            Picasso.with(mContext).load(R.drawable.price_high).into(priceView);
        }

        return convertView;
    }
}
