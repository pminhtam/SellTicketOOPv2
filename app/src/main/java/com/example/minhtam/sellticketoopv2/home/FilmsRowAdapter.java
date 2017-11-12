package com.example.minhtam.sellticketoopv2.home;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.minhtam.sellticketoopv2.film.FilmFragment;
import com.example.minhtam.sellticketoopv2.R;

import java.util.ArrayList;

/**
 * Created by Minh Tam on 11/5/2017.
 */

public class FilmsRowAdapter extends RecyclerView.Adapter<FilmsRowAdapter.ViewHolder> {

    Context context;
    ArrayList<ItemFilm> itemsRow;
    FragmentManager fragmentManager;
    String token;
    public FilmsRowAdapter(Context context,ArrayList<ItemFilm> itemsRow,FragmentManager fragmentManager,String token){
        this.context = context;
        this.itemsRow = itemsRow;
        this.fragmentManager = fragmentManager;
        this.token = token;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_film_,parent,false);
        return new FilmsRowAdapter.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtNameFilm.setText(itemsRow.get(position).getName());
        Glide.with(context)
                .load("https://tickett.herokuapp.com" + itemsRow.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgFilm);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "VI tri la " +String.valueOf(position), Toast.LENGTH_SHORT).show();
                String id = String.valueOf(itemsRow.get(position).getId());
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FilmFragment frag = new FilmFragment(context);
                fragmentTransaction.replace(R.id.frame,frag);
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                bundle.putString("token",token);
                frag.setArguments(bundle);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsRow.size();
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
