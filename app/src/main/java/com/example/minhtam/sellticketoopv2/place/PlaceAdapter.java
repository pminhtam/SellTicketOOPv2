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

import com.example.minhtam.sellticketoopv2.MainActivity;
import com.example.minhtam.sellticketoopv2.R;
import com.example.minhtam.sellticketoopv2.chooseseat.ChooseSeatFragment;

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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    MainActivity activity = (MainActivity)context;
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    FilmPlaceFragment frag = new FilmPlaceFragment();
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
