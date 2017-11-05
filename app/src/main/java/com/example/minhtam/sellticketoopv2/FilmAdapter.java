package com.example.minhtam.sellticketoopv2;

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

import java.util.ArrayList;

/**
 * Created by Minh Tam on 10/21/2017.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {

    Context context;
    ArrayList<ArrayList<ItemFilm>> items;
    FragmentManager fragmentManager;
    public FilmAdapter(Context context,ArrayList<ArrayList<ItemFilm>> items,FragmentManager fragmentManager){
        this.context = context;
        this.items = items;
        this.fragmentManager = fragmentManager;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_film_,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        holder.txtNameFilm.setText(items.get(position).get(position).getName());
        int a = items.get(position).size();
        holder.txtNameFilm.setText(String.valueOf(a));

//        String image = items.get(position).get(0).getImage();
        Glide.with(context)
                .load("https://tickett.herokuapp.com" + items.get(position).get(0).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgFilm);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "VI tri la " +String.valueOf(position), Toast.LENGTH_SHORT).show();
                String id = String.valueOf(items.get(position).get(0).getId());
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FilmFragment frag = new FilmFragment(context);
                fragmentTransaction.replace(R.id.frame,frag);
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                frag.setArguments(bundle);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
//        return items.size();
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
