package com.example.minhtam.sellticketoopv2.analyze;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.minhtam.sellticketoopv2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnalyzeFragment extends Fragment {


    public AnalyzeFragment() {
        // Required empty public constructor
    }
    String id;
    String token;
    Button btnAge,btnSex,btnSale;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analyze, container, false);
        id = getArguments().getString("id");
        token = getArguments().getString("token");

        btnAge = (Button) view.findViewById(R.id.btnAge);
        btnSex = (Button) view.findViewById(R.id.btnSex);
        btnSale = (Button) view.findViewById(R.id.btnSale);

        btnAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AgeAnalyzeFragment frag = new AgeAnalyzeFragment();
                fragmentTransaction.addToBackStack(frag.getClass().getSimpleName());
                fragmentTransaction.replace(R.id.frame,frag);
                fragmentTransaction.commit();
            }
        });
        btnSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SexAnalyzeFragment frag = new SexAnalyzeFragment();
                fragmentTransaction.addToBackStack(frag.getClass().getSimpleName());
                fragmentTransaction.replace(R.id.frame,frag);
                fragmentTransaction.commit();
            }
        });
        btnSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SaleAnalyzeFragment frag = new SaleAnalyzeFragment();
                fragmentTransaction.addToBackStack(frag.getClass().getSimpleName());
                fragmentTransaction.replace(R.id.frame,frag);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
