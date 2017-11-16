package com.example.minhtam.sellticketoopv2.chooseseat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.minhtam.sellticketoopv2.MainActivity;
import com.example.minhtam.sellticketoopv2.R;

import java.util.ArrayList;

/**
 * Created by trungdunghoang on 14/11/2017.
 */

public class SeatAdapter extends BaseAdapter {
    private final Context mContext;
    private final ArrayList<ItemSeat> seats;
    private final ArrayList<Integer> selectedSeats = new ArrayList<Integer>();
    private final ArrayList<Integer> selectedIds = new ArrayList<Integer>();
    public ArrayList<Integer> getSelectedIds() {
        return selectedIds;
    }

    public ArrayList<Integer> getSelectedSeats() {
        return selectedSeats;
    }

    public SeatAdapter(Context mContext, ArrayList<ItemSeat> seats) {
        this.mContext = mContext;
        this.seats = seats;
    }

    @Override
    public int getCount() {
        return seats.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void addSelectedItem(int selectedId, int selectedSeat) {
        this.selectedSeats.add(selectedSeat);
        this.selectedIds.add(selectedId);
        notifyDataSetChanged();
    }
    public void removeSelectedItem(int selectedId, int selectedSeat) {
        this.selectedSeats.remove((Integer)selectedSeat);
        this.selectedIds.remove((Integer)selectedId);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(mContext);
        if (view== null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) view;
        }
        final ItemSeat seat = seats.get(position);
        if (selectedSeats.contains(position)){
            imageView.setImageResource(R.drawable.selected);
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    DeleteSelectionDialog dialog = new DeleteSelectionDialog();
                    dialog.sendItem(SeatAdapter.this, position, seat);
                    dialog.show(((MainActivity)mContext).getFragmentManager(), "123");
                }
            });
        } else {
            if (seat.isChoose()) {
                imageView.setImageResource(R.drawable.is_choosen);
            } else if (seat.getLevel().equals("VIP")) {
                imageView.setImageResource(R.drawable.vip);
            } else if (seat.getLevel().equals("NORMAL")) {
                imageView.setImageResource(R.drawable.normal);
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    SeatDialog dialog = new SeatDialog();
                    dialog.sendIteamSeatSelected(SeatAdapter.this, position, seat);
                    dialog.show(((MainActivity) mContext).getFragmentManager(), "123");

                }
            });
        }
        return imageView;
    }

}
