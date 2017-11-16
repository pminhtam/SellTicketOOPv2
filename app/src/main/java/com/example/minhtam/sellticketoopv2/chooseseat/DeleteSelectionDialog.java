package com.example.minhtam.sellticketoopv2.chooseseat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.minhtam.sellticketoopv2.MainActivity;

/**
 * Created by trungdunghoang on 15/11/2017.
 */

public class DeleteSelectionDialog extends DialogFragment {
    SeatAdapter adapter;
    int position;
    ItemSeat itemSeat;

    public void sendItem(SeatAdapter adapter, int position, ItemSeat itemSeat) {
        this.adapter = adapter;
        this.position = position;
        this.itemSeat = itemSeat;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class because this dialog has a simple UI
        final MainActivity activity = (MainActivity) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Dialog will have "Make a selection" as the title
        builder.setMessage("Are you sure to delete this selection?")
                // An OK button that does nothing
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapter.removeSelectedItem(Integer.parseInt(itemSeat.getId()),position);
                    }
                })
                // A "Cancel" button that does nothing
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Nothing happening here either
                    }
                });
        // Create the object and return it
        return builder.create();
    }// End onCreateDialog
}
