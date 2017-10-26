package com.example.minhtam.sellticketoopv2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseSeatFragment extends Fragment {


    public ChooseSeatFragment() {
        // Required empty public constructor
    }

    ArrayList<Button> but;
    ArrayList<LinearLayout> listLinear;
    int nBut,nLinear;
    String[] abc = {"a","b","c","d","e","f","g","h","i","k","l","m","n"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_seat, container, false);
        but = new ArrayList<>();
        listLinear = new ArrayList<>();
        LinearLayout layout1 = (LinearLayout) view.findViewById(R.id.layout1);
        listLinear.add(layout1);
        LinearLayout layout2 = (LinearLayout) view.findViewById(R.id.layout2);
        listLinear.add(layout2);
        LinearLayout layout3 = (LinearLayout) view.findViewById(R.id.layout3);
        listLinear.add(layout3);
        LinearLayout layout4 = (LinearLayout) view.findViewById(R.id.layout4);
        listLinear.add(layout4);
        LinearLayout layout5 = (LinearLayout) view.findViewById(R.id.layout5);
        listLinear.add(layout5);
        LinearLayout layout6 = (LinearLayout) view.findViewById(R.id.layout6);
        listLinear.add(layout6);
        LinearLayout layout7 = (LinearLayout) view.findViewById(R.id.layout7);
        listLinear.add(layout7);
        LinearLayout layout8 = (LinearLayout) view.findViewById(R.id.layout8);
        listLinear.add(layout8);
        LinearLayout layout9 = (LinearLayout) view.findViewById(R.id.layout9);
        listLinear.add(layout9);
        LinearLayout layout10 = (LinearLayout) view.findViewById(R.id.layout10);
        listLinear.add(layout10);

        for (nLinear=0;nLinear<listLinear.size();nLinear++) {
            for(nBut=0;nBut<5;nBut++){
                final Button button = new Button(getActivity());
//                int stt = nBut*10 + nLinear;
//                button.setText(String.valueOf(stt));
//                button.setId((nBut*10 + nLinear)*1000);

                button.setTag((nBut*10 + nLinear));
                listLinear.get(nLinear).addView(button);
                button.setBackgroundResource(R.drawable.sofa);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String txt = button.getText().toString();
//                        int stt = Integer.parseInt(txt);
                        String tag = button.getTag().toString();
                        int stt = Integer.parseInt(tag);

//                        Toast.makeText(MainActivity.this,"cot "+stt%10 + " hang  "+stt/10,Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(),"cot: "+stt%10 + "\n hang : "+abc[stt/10],Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        return view;
    }

}
