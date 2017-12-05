package com.example.minhtam.sellticketoopv2.seller.historysell;

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
 * Created by Minh Tam on 12/5/2017.
 */

public class HistorySellAdapter extends RecyclerView.Adapter<HistorySellAdapter.ViewHolder>{
    Context context;
    ArrayList<ItemHistorySell> itemsHistorySell;
    public HistorySellAdapter(Context context,ArrayList<ItemHistorySell> items){
        this.context = context;
        itemsHistorySell = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(layoutInflater.inflate(R.layout.item_history_sell,parent,false));    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(ApiUrl.URL +itemsHistorySell.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgSellHistory);
        holder.txtNameFilmSellHistory.setText(itemsHistorySell.get(position).getNameFilm());
        holder.txtLocationSellHistory.setText(itemsHistorySell.get(position).getNameLocation());
        holder.txtPriceSellHistory.setText(String.valueOf(itemsHistorySell.get(position).getPrice()));
        holder.txtRowColSellHistory.setText("Hang la "+ itemsHistorySell.get(position).getRow() + "\nCot la "+ itemsHistorySell.get(position).getColumn());
        holder.txtUserBuyNameSellHistory.setText("Người mua:" + itemsHistorySell.get(position).getUser_buy_name());

        holder.txtTimeBeginSellHistory.setText("Thời gian bắt đâu:" + itemsHistorySell.get(position).getTime_begin());
        holder.txtTimeEndSellHistory.setText("Thời gian kết thúc:" +itemsHistorySell.get(position).getTime_end());
        holder.txtTimeUserBookSellHistory.setText("Thời gian bmua vé:" +itemsHistorySell.get(position).getTime_user_book());
    }

    @Override
    public int getItemCount() {
        return itemsHistorySell.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgSellHistory;
        TextView txtNameFilmSellHistory,txtLocationSellHistory,txtPriceSellHistory,txtRowColSellHistory,txtUserBuyNameSellHistory;
        TextView txtTimeBeginSellHistory,txtTimeEndSellHistory,txtTimeUserBookSellHistory;
        public ViewHolder(View itemView) {
            super(itemView);
            imgSellHistory = (ImageView) itemView.findViewById(R.id.imgSellHistory);
            txtNameFilmSellHistory = (TextView) itemView.findViewById(R.id.txtNameFilmSellHistory);
            txtLocationSellHistory = (TextView) itemView.findViewById(R.id.txtLocationSellHistory);
            txtPriceSellHistory = (TextView) itemView.findViewById(R.id.txtPriceSellHistory);
            txtRowColSellHistory = (TextView) itemView.findViewById(R.id.txtRowColSellHistory);
            txtUserBuyNameSellHistory = (TextView) itemView.findViewById(R.id.txtUserBuyNameSellHistory);
            txtTimeBeginSellHistory = (TextView) itemView.findViewById(R.id.txtTimeBeginSellHistory);
            txtTimeEndSellHistory = (TextView) itemView.findViewById(R.id.txtTimeEndSellHistory);
            txtTimeUserBookSellHistory = (TextView) itemView.findViewById(R.id.txtTimeUserBookSellHistory);
        }
    }
}
