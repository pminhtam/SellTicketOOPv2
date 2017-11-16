package com.example.minhtam.sellticketoopv2.chooseseat;

import android.app.Dialog;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.minhtam.sellticketoopv2.MainActivity;
import com.example.minhtam.sellticketoopv2.R;

/**
 * Created by trungdunghoang on 14/11/2017.
 */

public class SeatDialog extends DialogFragment {
    private ItemSeat itemSeat;
    private int position;
    private SeatAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i("info", "dialog created");
        final MainActivity activity = (MainActivity)getActivity();
        // Use the Builder class because this dialog has a simple UI
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_seat_booking, null);

        final TextView txtSeatState = (TextView) dialogView.findViewById(R.id.txtSeatState);
        final TextView txtSeatCategory = (TextView) dialogView.findViewById(R.id.txtSeatCategory);
        final TextView txtSeatPrice = (TextView) dialogView.findViewById(R.id.txtSeatPrice);
        final Button btnSelect = (Button) dialogView.findViewById(R.id.btnSelect);
        final Button btnClose = (Button) dialogView.findViewById(R.id.btnClose);

        if (itemSeat.isChoose()) {
            txtSeatState.setText("Booked");
            btnSelect.setVisibility(View.GONE);
        } else {
            txtSeatState.setText("Available");
        }
        txtSeatCategory.setText(itemSeat.getLevel());
        txtSeatPrice.setText(""+itemSeat.getPrice());
        btnSelect.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemSeat.isChoose()) {
//                if (Integer.valueOf(activity.getUserMoney()) < itemSeat.getPrice() || itemSeat.isChoose()) {
                    BookFailDialog dialog = new BookFailDialog();
                    dialog.show(activity.getFragmentManager(), "123");
                } else {
//                    SeatAdapter adapter = (SeatAdapter)getHost();
                    adapter.addSelectedItem(position);
                    dismiss();
                }
            }
        });
        btnClose.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(dialogView);
        return builder.create();
    }
    public void sendIteamSeatSelected(SeatAdapter adapter, int position, ItemSeat itemSeat) {
        this.adapter = adapter;
        this.itemSeat = itemSeat;
        this.position = position;
    }

}