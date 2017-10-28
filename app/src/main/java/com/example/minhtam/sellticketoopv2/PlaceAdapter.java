package com.example.minhtam.sellticketoopv2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by ThanhDat on 10/27/2017.
 */

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    Context context;
    ArrayList<ItemPlace> items;

    public PlaceAdapter(Context context, ArrayList<ItemPlace> items){
        this.context = context;
        this.items = items;
    }
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_place_, parent, false);
        return new PlaceAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlaceAdapter.ViewHolder holder, int position) {
        holder.txtNamePlace.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNamePlace;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNamePlace = (TextView) itemView.findViewById(R.id.txtNamePlace);
        }
    }
}
