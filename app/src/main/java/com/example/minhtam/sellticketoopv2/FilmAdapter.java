package com.example.minhtam.sellticketoopv2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Minh Tam on 10/21/2017.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {

    Context context;
    ArrayList<ItemFilm> items;

    public FilmAdapter(Context context,ArrayList<ItemFilm> items){
        this.context = context;
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_film_,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtNameFilm.setText(items.get(position).getName());
        String image = items.get(position).getImage();
        Glide.with(context)
                .load("https://tickett.herokuapp.com" + items.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgFilm);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNameFilm;
        ImageView imgFilm;
        public ViewHolder(View itemView) {
            super(itemView);
            txtNameFilm = (TextView) itemView.findViewById(R.id.txtNameFilm);
            imgFilm = (ImageView) itemView.findViewById(R.id.imgFilm);
        }
    }
}
