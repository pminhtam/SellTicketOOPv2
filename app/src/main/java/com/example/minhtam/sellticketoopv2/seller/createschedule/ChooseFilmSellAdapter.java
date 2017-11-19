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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.minhtam.sellticketoopv2.ApiUrl;
import com.example.minhtam.sellticketoopv2.MainActivity;
import com.example.minhtam.sellticketoopv2.R;

import java.util.ArrayList;

/**
 * Created by Minh Tam on 11/19/2017.
 */

public class ChooseFilmSellAdapter extends RecyclerView.Adapter<ChooseFilmSellAdapter.ViewHolder>{

    ArrayList<ItemChooseFilmSell> items;
    Context context;
    String token;

    public ChooseFilmSellAdapter(Context context,ArrayList<ItemChooseFilmSell> items, String token){
        this.context = context;
        this.items = items;
        this.token = token;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_film_,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtNameFilm.setText(items.get(position).getFilmName());
        Glide.with(context)
                .load(ApiUrl.URL + items.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgFilm);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity)context;
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ChooseLocationFragment frag = new ChooseLocationFragment(items.get(position),token);
                fragmentTransaction.replace(R.id.frame,frag);
                fragmentTransaction.addToBackStack(frag.getClass().getSimpleName());
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgFilm;
        TextView txtNameFilm;
        public ViewHolder(View itemView) {
            super(itemView);
            imgFilm = (ImageView) itemView.findViewById(R.id.imgFilm);
            txtNameFilm = (TextView) itemView.findViewById(R.id.txtNameFilm);
        }
    }
}
