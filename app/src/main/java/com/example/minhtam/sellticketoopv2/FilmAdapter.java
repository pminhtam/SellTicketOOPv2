package com.example.minhtam.sellticketoopv2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by Minh Tam on 10/21/2017.
 */

public class FilmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<ArrayList<ItemFilm>> items;
    FragmentManager fragmentManager;
    FilmsRowAdapter adapter;
    public FilmAdapter(Context context,ArrayList<ArrayList<ItemFilm>> items,FragmentManager fragmentManager){
        this.context = context;
        this.items = items;
        this.fragmentManager = fragmentManager;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case 0: return new ViewHolder0(layoutInflater.inflate(R.layout.item_films_header,parent,false));
            case 1: return new ViewHolder1(layoutInflater.inflate(R.layout.item_films_row,parent,false));
        }
        return new ViewHolder1(layoutInflater.inflate(R.layout.item_films_row,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()){
            case 0:
                ViewHolder0 viewHolder0 = (ViewHolder0) holder;
                //load hình ảnh
                Glide.with(context)
                    .load("https://tickett.herokuapp.com" + items.get(position).get(0).getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder0.imgFilmsHeader0);
                Glide.with(context)
                        .load("https://tickett.herokuapp.com" + items.get(position).get(1).getImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(viewHolder0.imgFilmsHeader1);
                Glide.with(context)
                        .load("https://tickett.herokuapp.com" + items.get(position).get(2).getImage())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(viewHolder0.imgFilmsHeader2);
                //
                // Them su kien click vao hinh anh
                viewHolder0.imgFilmsHeader0.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                viewHolder0.imgFilmsHeader1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = String.valueOf(items.get(position).get(1).getId());
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        FilmFragment frag = new FilmFragment(context);
                        fragmentTransaction.replace(R.id.frame,frag);
                        Bundle bundle = new Bundle();
                        bundle.putString("id",id);
                        frag.setArguments(bundle);
                        fragmentTransaction.commit();
                    }
                });
                viewHolder0.imgFilmsHeader2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = String.valueOf(items.get(position).get(2).getId());
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        FilmFragment frag = new FilmFragment(context);
                        fragmentTransaction.replace(R.id.frame,frag);
                        Bundle bundle = new Bundle();
                        bundle.putString("id",id);
                        frag.setArguments(bundle);
                        fragmentTransaction.commit();
                    }
                });
                //

                break;
            case 1:
                ViewHolder1 viewHolder1 = (ViewHolder1) holder;
                viewHolder1.rcFilmsRow.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

                viewHolder1.rcFilmsRow.setLayoutManager(layoutManager);
                adapter = new FilmsRowAdapter(context, items.get(position),fragmentManager);
                viewHolder1.rcFilmsRow.setAdapter(adapter);

        }
    }

    @Override
    public int getItemCount() {
//        return items.size();
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(items.get(position).size() == 3){
            return 0;
        }
        return 1;
    }

    public class ViewHolder0 extends RecyclerView.ViewHolder{   // phần đầu của home
        ViewFlipper viewFlipperFilmsHeader;
        ImageView imgFilmsHeader0,imgFilmsHeader1,imgFilmsHeader2;
        Animation in, out;

        public ViewHolder0(View itemView) {
            super(itemView);
            viewFlipperFilmsHeader = (ViewFlipper) itemView.findViewById(R.id.viewFlipperFilmsHeader);
            imgFilmsHeader0 = (ImageView) itemView.findViewById(R.id.imgFilmsHeader0);
            imgFilmsHeader1 = (ImageView) itemView.findViewById(R.id.imgFilmsHeader1);
            imgFilmsHeader2 = (ImageView) itemView.findViewById(R.id.imgFilmsHeader2);
            in = AnimationUtils.loadAnimation(context,R.anim.fade_in);
            out = AnimationUtils.loadAnimation(context,R.anim.fade_out);
            viewFlipperFilmsHeader.setOutAnimation(out);
            viewFlipperFilmsHeader.setInAnimation(in);
            viewFlipperFilmsHeader.setFlipInterval(3000);
            viewFlipperFilmsHeader.setAutoStart(true);
        }
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder{       //phần thân
        RecyclerView rcFilmsRow;
        public ViewHolder1(View itemView) {
            super(itemView);
            rcFilmsRow = (RecyclerView) itemView.findViewById(R.id.rcFilmsRow);
        }
    }
}
