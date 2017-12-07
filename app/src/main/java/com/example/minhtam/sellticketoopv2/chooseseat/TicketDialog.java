package com.example.minhtam.sellticketoopv2.chooseseat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.minhtam.sellticketoopv2.ApiUrl;
import com.example.minhtam.sellticketoopv2.R;

import java.util.ArrayList;

/**
 * Created by Minh Tam on 12/7/2017.
 */

@SuppressLint("ValidFragment")
public class TicketDialog extends DialogFragment {
    ImageView imgDialogTicket;
    Button btnDialogTicketClose;
    String userName;
    ArrayList<Integer> selectedIds;
    Context context;
    public TicketDialog(ArrayList<Integer> selectedIds, String userName, Context context){
        this.selectedIds = selectedIds;
        this.userName = userName;
        this.context = context;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_ticket, null);


        imgDialogTicket = (ImageView) dialogView.findViewById(R.id.imgDialogTicket);
        btnDialogTicketClose = (Button) dialogView.findViewById(R.id.btnDialogTicketClose);

        Glide.with(context)
                .load("http://chart.googleapis.com/chart?cht=qr&chs=500x500&chl="+selectedIds.toString() + userName)
                .into(imgDialogTicket);
        btnDialogTicketClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        builder.setView(dialogView);
        return builder.create();
    }
}
