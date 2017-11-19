package com.example.minhtam.sellticketoopv2.seller.createschedule;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.minhtam.sellticketoopv2.MainActivity;
import com.example.minhtam.sellticketoopv2.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Minh Tam on 11/19/2017.
 */

public class ChooseRoomAdapter extends RecyclerView.Adapter<ChooseRoomAdapter.ViewHolder>{
    ArrayList<ItemRoom> items;
    ItemChooseFilmSell itemChooseFilm;
    String token;
    Context context;
    public  ChooseRoomAdapter(Context context, ArrayList<ItemRoom> items, ItemChooseFilmSell itemChooseFilm, String token){
        this.context = context;
        this.items = items;
        this.itemChooseFilm = itemChooseFilm;
        this.token = token;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.film_element_schedules,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtFilmElementSchedules.setText(items.get(position).getName());
        holder.txtFilmElementSchedules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemChooseFilm.setRoomId(items.get(position).getId());
                MainActivity activity = (MainActivity)context;
                itemChooseFilm.setLocationId(String.valueOf(items.get(position).getId()));
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CreateScheduleFragment frag = new CreateScheduleFragment(itemChooseFilm,token);
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
        TextView txtFilmElementSchedules;
        public ViewHolder(View itemView) {
            super(itemView);
            txtFilmElementSchedules = (TextView) itemView.findViewById(R.id.txtFilmElementSchedules);
        }
    }
}
