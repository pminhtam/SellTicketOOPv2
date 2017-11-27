package com.example.minhtam.sellticketoopv2.seller.createschedule;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.minhtam.sellticketoopv2.R;

import com.example.minhtam.sellticketoopv2.MainActivity;
import com.example.minhtam.sellticketoopv2.place.FilmPlaceFragment;
import com.example.minhtam.sellticketoopv2.place.ItemPlace;

import java.util.ArrayList;


/**
 * Created by Minh Tam on 11/19/2017.
 */

public class ChooseLocationAdapter extends RecyclerView.Adapter<ChooseLocationAdapter.ViewHolder>{

    Context context;
    ArrayList<ItemPlace> items;
    ItemChooseFilmSell itemChooseFilm;
    String token;
    public ChooseLocationAdapter(Context context, ArrayList<ItemPlace> items, ItemChooseFilmSell itemChooseFilm, String token){
        this.context = context;
        this.items = items;
        this.itemChooseFilm = itemChooseFilm;
        this.token = token;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_place_, parent, false);
        return new ViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtNamePlace.setText(items.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity)context;
                itemChooseFilm.setLocationId(String.valueOf(items.get(position).getId()));
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ChooseRoomFragment frag = new ChooseRoomFragment(itemChooseFilm,token);
                fragmentTransaction.replace(R.id.frame,frag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNamePlace;
        public ViewHolder(View itemView) {
            super(itemView);
            txtNamePlace = (TextView) itemView.findViewById(R.id.txtNamePlace);

        }
    }
}
