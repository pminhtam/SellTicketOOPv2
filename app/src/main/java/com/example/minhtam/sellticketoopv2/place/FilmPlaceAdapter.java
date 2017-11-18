package com.example.minhtam.sellticketoopv2.place;

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
import com.example.minhtam.sellticketoopv2.MainActivity;
import com.example.minhtam.sellticketoopv2.R;
import com.example.minhtam.sellticketoopv2.chooseseat.ChooseSeatFragment;
import com.example.minhtam.sellticketoopv2.chooseseat.ChooseSeatFragmentDemo;
import com.example.minhtam.sellticketoopv2.home.ItemFilm;

import java.util.ArrayList;

/**
 * Created by trungdunghoang on 14/11/2017.
 */

public class FilmPlaceAdapter extends RecyclerView.Adapter<FilmPlaceAdapter.ViewHolder> {
    Context context;
    ArrayList<ItemFilm> items;

    public FilmPlaceAdapter(Context context, ArrayList<ItemFilm> items){
        this.context = context;
        this.items = items;
    }
    @Override
    public FilmPlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_film_place, parent, false);
        return new FilmPlaceAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FilmPlaceAdapter.ViewHolder holder, int position) {
        holder.txtFilmPlaceName.setText(items.get(position).getName());
        holder.txtFilmPlaceDetail.setText(items.get(position).getName());
        Glide.with(context)
                .load("http://tickett.cloudapp.net" + items.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgFilmPlace);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtFilmPlaceName;
        ImageView imgFilmPlace;
        TextView txtFilmPlaceDetail;
        public ViewHolder(View itemView) {
            super(itemView);
            txtFilmPlaceName = (TextView) itemView.findViewById(R.id.txtFilmPlaceName);
            txtFilmPlaceDetail = (TextView) itemView.findViewById(R.id.txtFilmPlaceDetail);
            imgFilmPlace = (ImageView) itemView.findViewById(R.id.imgFilmPlace);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    MainActivity activity = (MainActivity)context;
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ChooseSeatFragmentDemo frag = new ChooseSeatFragmentDemo();
                    Bundle bundle = new Bundle();
                    bundle.putString("token", activity.getToken());
                    bundle.putString("id", ""+items.get(position).getId());
                    frag.setArguments(bundle);
                    fragmentTransaction.replace(R.id.frame,frag);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }
    }
}

