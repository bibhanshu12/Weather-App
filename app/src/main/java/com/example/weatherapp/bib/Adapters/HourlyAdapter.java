package com.example.weatherapp.bib.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherapp.bib.Domins.Hourly;
import com.example.weatherapp.bib.R;

import java.util.ArrayList;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.viewHolder> {

    ArrayList<Hourly> item;
    Context context;

    public HourlyAdapter(ArrayList<Hourly> item) {
        this.item = item;

    }

    @NonNull
    @Override
    public HourlyAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_hourly,parent,false );

        return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyAdapter.viewHolder holder, int position) {
        holder.hourtxt.setText(item.get(position).getHour());
        holder.temptxt.setText(item.get(position).getPicpath());

        int drawableresourceid = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            drawableresourceid = holder.itemView.getResources()
                    .getIdentifier(item.get(position).getPicpath(),"drawable",holder.itemView.getContext().getOpPackageName());
        }

        // Check if the context is not null before loading the image
        if (holder.itemView.getContext() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(drawableresourceid)
                    .into(holder.picpathimg);
        }
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class viewHolder extends  RecyclerView.ViewHolder {
        TextView hourtxt, temptxt;
        ImageView picpathimg;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            hourtxt=itemView.findViewById(R.id.hourlytime);
            temptxt=itemView.findViewById(R.id.temp);
            picpathimg=itemView.findViewById(R.id.picImage);

        }
    }
}
