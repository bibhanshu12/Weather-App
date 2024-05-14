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
import com.example.weatherapp.bib.Domins.FutureDomains;
import com.example.weatherapp.bib.Domins.Hourly;
import com.example.weatherapp.bib.R;

import java.util.ArrayList;

public class FutureAdapters extends RecyclerView.Adapter<FutureAdapters.viewHolder> {

    ArrayList<FutureDomains> item;
    Context context;

    public FutureAdapters(ArrayList<FutureDomains> item) {
        this.item = item;
    }

    @NonNull
    @Override
    public FutureAdapters.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.futurelayouthourly,parent,false );
        context= parent.getContext();

        return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FutureAdapters.viewHolder holder, int position) {

        holder.days.setText(item.get(position).getDay());
        holder.mintemp.setText(String.valueOf(item.get(position).getMintemp()));
        holder.maxtemp.setText(String.valueOf(item.get(position).getMaxtemp()));
        holder.status.setText(item.get(position).getStatus());


        //Api responses



        int drawableresourceid = holder.itemView.getResources()
                .getIdentifier(item.get(position).getDay(), "drawable", holder.itemView.getContext().getPackageName());


        // Check if drawableresourceid is valid
        if (drawableresourceid != 0) {
                Glide.with(context)
                        .load(drawableresourceid)
                        .into(holder.futuepicpath);
        } else {
            // Handle the case where the resource is not found
            // For example, load a placeholder image or show an error message
        }
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public class viewHolder extends  RecyclerView.ViewHolder {
        TextView days,status,maxtemp,mintemp;
        ImageView futuepicpath;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            days=itemView.findViewById(R.id.future_days);
            status=itemView.findViewById(R.id.weatherType);
            maxtemp=itemView.findViewById(R.id.maximumtemp);
            mintemp=itemView.findViewById(R.id.minimumtemp);
            futuepicpath=itemView.findViewById(R.id.picImage);

        }
    }
}
