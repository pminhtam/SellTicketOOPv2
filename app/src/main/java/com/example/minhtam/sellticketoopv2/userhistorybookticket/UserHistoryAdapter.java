package com.example.minhtam.sellticketoopv2.userhistorybookticket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.minhtam.sellticketoopv2.ApiUrl;
import com.example.minhtam.sellticketoopv2.R;

import java.util.ArrayList;

/**
 * Created by Minh Tam on 11/18/2017.
 */

public class UserHistoryAdapter extends RecyclerView.Adapter<UserHistoryAdapter.ViewHolder> {
    Context context;
    ArrayList<ItemUserHistoryBookTicket> itemUserHistoryBookTickets;

    public UserHistoryAdapter(Context context,ArrayList<ItemUserHistoryBookTicket> itemUserHistoryBookTickets){
        this.context = context;
        this.itemUserHistoryBookTickets = itemUserHistoryBookTickets;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.item_user_history,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(ApiUrl.URL +itemUserHistoryBookTickets.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgUserHistory);
        holder.txtNameFilmUserHistory.setText(itemUserHistoryBookTickets.get(position).getNameFilm());
        holder.txtLocationUserHistory.setText(itemUserHistoryBookTickets.get(position).getNameLocation());
        holder.txtPriceUserHistory.setText(String.valueOf(itemUserHistoryBookTickets.get(position).getPrice()));
        holder.txtRowColUserHistory.setText("Hang la "+ itemUserHistoryBookTickets.get(position).getRow() + "\n Cot la "+ itemUserHistoryBookTickets.get(position).getColumn());
    }

    @Override
    public int getItemCount() {
        return itemUserHistoryBookTickets.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgUserHistory;
        TextView txtNameFilmUserHistory,txtLocationUserHistory,txtPriceUserHistory,txtRowColUserHistory;
        public ViewHolder(View itemView) {
            super(itemView);
            imgUserHistory = (ImageView) itemView.findViewById(R.id.imgUserHistory);
            txtNameFilmUserHistory = (TextView) itemView.findViewById(R.id.txtNameFilmUserHistory);
            txtLocationUserHistory = (TextView) itemView.findViewById(R.id.txtLocationUserHistory);
            txtPriceUserHistory = (TextView) itemView.findViewById(R.id.txtPriceUserHistory);
            txtRowColUserHistory = (TextView) itemView.findViewById(R.id.txtRowColUserHistory);
        }
    }
}
