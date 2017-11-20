package com.example.minhtam.sellticketoopv2.filmcreate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.example.minhtam.sellticketoopv2.MainActivity;
import com.example.minhtam.sellticketoopv2.chooseseat.ItemSeat;
import com.example.minhtam.sellticketoopv2.chooseseat.SeatAdapter;

/**
 * Created by trungdunghoang on 20/11/2017.
 */

public class ConfirmCreateDialog extends DialogFragment {
    CreateFilmFragment parentFragment;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class because this dialog has a simple UI
        final MainActivity activity = (MainActivity) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Dialog will have "Make a selection" as the title
        builder.setMessage("Are you sure to create this film?")
                // An OK button that does nothing
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    public void onClick(DialogInterface dialog, int id) {
                        parentFragment.sumbitCreateFilm();
                        dismiss();
                    }
                })
                // A "Cancel" button that does nothing
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       dismiss();
                    }
                });
        // Create the object and return it
        return builder.create();
    }// End onCreateDialog

    public void setParentFragment(CreateFilmFragment parentFragment) {
        this.parentFragment = parentFragment;
    }
}
