package com.example.minhtam.sellticketoopv2.filmcreate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.minhtam.sellticketoopv2.MainActivity;
import com.example.minhtam.sellticketoopv2.R;

/**
 * Created by trungdunghoang on 18/11/2017.
 */

public class CreateFilmFragment extends Fragment {

    String token;
    EditText editName, editKind, editDuration, editRlDate, editDescription;
    Button btnImport, btnCreate;
    ImageView imgFilm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_film, container, false);
        token = getArguments().getString("token");
        findView(view);


        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmCreateDialog dialog = new ConfirmCreateDialog();
                dialog.show(((MainActivity)getActivity()).getFragmentManager(), "123");
            }
        });
        return view;
    }


    void findView(View view) {
        editName = (EditText) view.findViewById(R.id.editName);
        editKind = (EditText) view.findViewById(R.id.editKind);
        editDuration = (EditText) view.findViewById(R.id.editDuration);
        editRlDate = (EditText) view.findViewById(R.id.editRlDate);
        editDescription = (EditText) view.findViewById(R.id.editDescription);
        btnImport = (Button) view.findViewById(R.id.btnImport);
        btnCreate = (Button) view.findViewById(R.id.btnCreate);
        imgFilm = (ImageView) view.findViewById(R.id.imgFilm);
    }
}