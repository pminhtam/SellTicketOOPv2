package com.example.minhtam.sellticketoopv2.film;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
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
import com.example.minhtam.sellticketoopv2.R;
import com.example.minhtam.sellticketoopv2.chooseseat.ChooseSeatFragment;
import com.example.minhtam.sellticketoopv2.chooseseat.ChooseSeatFragmentDemo;

import java.util.ArrayList;

/**
 * Created by Minh Tam on 11/10/2017.
 */

public class FilmElementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList itemFilmElement;
    String token;
    FragmentManager fragmentManager;
    public FilmElementAdapter(Context context, ArrayList itemFilmElement, String token,FragmentManager fragmentManager){
        this.context = context;
        this.itemFilmElement = itemFilmElement;
        this.token= token;
        this.fragmentManager = fragmentManager;
    }
    ItemFilmSchedules itemFilmSchedules;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case 0: return new ViewHolderInfo(layoutInflater.inflate(R.layout.film_element_info,parent,false));
        }
        return new ViewHolderSchedules(layoutInflater.inflate(R.layout.film_element_schedules,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                ViewHolderInfo viewHolderInfo = (ViewHolderInfo) holder;
                ItemFilmElementInfo itemFilmElementInfo = (ItemFilmElementInfo) itemFilmElement.get(position);
                viewHolderInfo.txtTimeFilmElement.setText(itemFilmElementInfo.getDuration());
                viewHolderInfo.txtContentFilmElement.setText(itemFilmElementInfo.getContent());
                viewHolderInfo.txtNameFilmElement.setText(itemFilmElementInfo.getName());
                String image = itemFilmElementInfo.getImage();
                Glide.with(context)
                        .load(ApiUrl.URL +image)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(viewHolderInfo.imgFilmElement);
                break;
            case 1:
                ViewHolderSchedules viewHolderSchedules = (ViewHolderSchedules) holder;
                itemFilmSchedules = (ItemFilmSchedules) itemFilmElement.get(position);
                viewHolderSchedules.txtFilmElementSchedules.setText(itemFilmSchedules.getNameLocation());
                viewHolderSchedules.txtFilmElementSchedules.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        ChooseSeatFragmentDemo frag = new ChooseSeatFragmentDemo();
                        Bundle bundle = new Bundle();
                        bundle.putString("id",itemFilmSchedules.getIdSchedules());
                        bundle.putString("token",token);
                        frag.setArguments(bundle);
                        fragmentTransaction.addToBackStack(frag.getClass().getSimpleName());
                        fragmentTransaction.replace(R.id.frame,frag);
                        fragmentTransaction.commit();
                    }
                });
        }

    }

    @Override
    public int getItemCount() {
        return itemFilmElement.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(itemFilmElement.get(position) instanceof ItemFilmElementInfo){
            return 0;
        }
        else
            return 1;
    }

    public class ViewHolderInfo extends RecyclerView.ViewHolder{
        ImageView imgFilmElement;
        TextView txtNameFilmElement,txtTimeFilmElement,txtContentFilmElement;
        public ViewHolderInfo(View itemView) {
            super(itemView);
            imgFilmElement = (ImageView) itemView.findViewById(R.id.imgFilmElement);
            txtNameFilmElement = (TextView) itemView.findViewById(R.id.txtNameFilmElement);
            txtTimeFilmElement = (TextView) itemView.findViewById(R.id.txtTimeFilmElement);
            txtContentFilmElement = (TextView) itemView.findViewById(R.id.txtContentFilmElement);
        }
    }
    public class ViewHolderSchedules extends RecyclerView.ViewHolder{
        TextView txtFilmElementSchedules;
        public ViewHolderSchedules(View itemView) {
            super(itemView);
            txtFilmElementSchedules = (TextView) itemView.findViewById(R.id.txtFilmElementSchedules);
        }
    }
}
