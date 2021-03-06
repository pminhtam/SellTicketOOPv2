package com.example.minhtam.sellticketoopv2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by trungdunghoang on 14/11/2017.
 */

public class SignoutDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class because this dialog has a simple UI
        final MainActivity activity = (MainActivity) this.getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Dialog will have "Make a selection" as the title
        builder.setMessage("Are you sure to signout?")
                // An OK button that does nothing
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    activity.setUserData("");
                    activity.getUserDataFromToken();
                    activity.setNavigationDetail();
                    activity.moveToLogInFragment();
                    activity.hideItem(R.id.nav_update_info);
                    activity.hideItem(R.id.nav_user_history);
                    activity.hideItem(R.id.nav_seller_create_schedule);
                    activity.hideItem(R.id.nav_seller_create_film);
                    activity.hideItem(R.id.nav_history_sell);

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
