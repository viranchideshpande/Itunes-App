package com.example.nirmalsella.itunesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Nirmal Sella on 23-10-2017.
 */

public class Filtered extends RecyclerView.Adapter<Filtered.ViewHolder> {
    static ArrayList<Itunes> mData;
    static ArrayList<Itunes> list;


    public Filtered(ArrayList<Itunes> mData, ArrayList<Itunes> list) {
        this.mData = mData;
        this.list=list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView largeImg,del,price;
        TextView textViewName, textViewCost;
        Itunes itunes;
        Long id;
        DatabaseManager dm;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itunes = itunes;
            largeImg = (ImageView) itemView.findViewById(R.id.largeImg);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewCost = (TextView) itemView.findViewById(R.id.textViewCost);
            price = (ImageView) itemView.findViewById(R.id.priceImg);
            del = (ImageView) itemView.findViewById(R.id.deleteImg);
            dm = new DatabaseManager(itemView.getContext());


            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dm.deleteApp(itunes.get_id());
                    list.add(itunes);

                    mData = (ArrayList<Itunes>) dm.getListOfApps();
                    MainActivity.setProgress(mData);
                }
            });
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontaltunes,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Itunes tunes = mData.get(position);

        Picasso.with(holder.itemView.getContext()).load(tunes.getLargeImage()).into(holder.largeImg);
        holder.textViewName.setText(tunes.getTitle());
        holder.textViewCost.setText("Price: USD " +String.valueOf(tunes.getPrice()));
        holder.itunes = tunes;
        if(tunes.getPrice() < 2){
            Picasso.with(holder.itemView.getContext()).load(R.drawable.price_low).into(holder.price);
        } else if(tunes.getPrice() < 6){
            Picasso.with(holder.itemView.getContext()).load(R.drawable.price_medium).into(holder.price);
        }else{
            Picasso.with(holder.itemView.getContext()).load(R.drawable.price_high).into(holder.price);
        }
        Picasso.with(holder.itemView.getContext()).load(R.drawable.delete_icon).into(holder.del);

        holder.id = tunes.get_id();
        /*holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.dm.deleteApp(holder.id);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



}