package com.example.minhtam.sellticketoopv2.chooseseat;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.minhtam.sellticketoopv2.ApiUrl;
import com.example.minhtam.sellticketoopv2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseSeatFragment extends Fragment {


    public ChooseSeatFragment() {
        // Required empty public constructor
    }
    String id;
    String token;
    ArrayList<Button> but;
    ArrayList<LinearLayout> listLinear;
    ArrayList<ItemSeat> itemSeats;
    int nBut,nLinear;
    String[] abc = {"a","b","c","d","e","f","g","h","i","k","l","m","n"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_seat, container, false);
        but = new ArrayList<>();
        itemSeats = new ArrayList<>();
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
        id = getArguments().getString("id");
        token = getArguments().getString("token");


        Log.e("ChooseSeatFragment",token);


//
//        for (nLinear=0;nLinear<listLinear.size();nLinear++) {
//            for(nBut=0;nBut<5;nBut++){
//                final Button button = new Button(getActivity());
////                int stt = nBut*10 + nLinear;
////                button.setText(String.valueOf(stt));
////                button.setId((nBut*10 + nLinear)*1000);
//
//                button.setTag((nBut*10 + nLinear));
//
//                listLinear.get(nLinear).addView(button);
//                button.setBackgroundResource(R.drawable.sofa);
//                but.add(button);
////                button.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        String tag = button.getTag().toString();
////                        int stt = Integer.parseInt(tag);
////                        Toast.makeText(getActivity(),"cot: "+stt%10 + "\n hang : "+abc[stt/10],Toast.LENGTH_SHORT).show();
////                    }
////                });
//            }
//        }


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
            new GetSeat().execute(ApiUrl.getSchedule(id));
            }
        });


        return view;
    }
    //Lay thông tin ghế
    class GetSeat extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            Request request = new Request.Builder()
                .url(url).addHeader("Authorization",token).build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        JSONArray seats;
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject body = new JSONObject(s);
                int code = body.getInt("code");
                Log.e("ChooseSeatFragment", "Lay xong ghe");
                if (code == 1) {
                    JSONObject data = body.getJSONObject("data");
//                    Toast.makeText(getActivity(), data.getString("seats"), Toast.LENGTH_SHORT).show();
                    seats = data.getJSONArray("seats");
                    Toast.makeText(getActivity(), seats.getString(0), Toast.LENGTH_SHORT).show();
                    Log.e("ChooseSeatFragment", seats.getString(1));
                    Log.e("ChooseSeatFragment", "Dao dai cua du lieu seats la " + seats.length());
                    Log.e("ChooseSeatFragment", seats.getJSONArray(1).getString(0));

                    int i = 0;
                    for(i=0;i<seats.length();i++){
                        JSONArray seat = seats.getJSONArray(i);
                        Log.e("ChooseSeatFragment", seats.getJSONArray(i).getString(0));
                        String row = seat.getString(0);
                        Log.e("ChooseSeatFragment", "Row la " +row);

                        String column = seat.getString(1);
                        Log.e("ChooseSeatFragment", "column la " + column);

                        Log.e("ChooseSeatFragment", "column la " + column);

                        Log.e("ChooseSeatFragment", "column la " + column);

                        ItemSeat itemSeat = new ItemSeat(row,column);
                        itemSeats.add(itemSeat);
                        int rowSeat = i%10;
                        Log.e("ChooseSeatFragment", "rowSeat la " + rowSeat);

                        int columnSeat = i/10;
                        Log.e("ChooseSeatFragment", "columnSeat la " + columnSeat);

                        final Button button = new Button(getActivity());
                        Log.e("ChooseSeatFragment", "Tao Button la " );

                        button.setBackgroundResource(R.drawable.sofa);
                        button.setTag(R.id.tagSeatColumn,row);
                        button.setTag(R.id.tagSeatRow,column);
                        listLinear.get(columnSeat).addView(button);
                        Log.e("ChooseSeatFragment","Vua them ghe hang "+row + "  Cot:"+column);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getActivity(),"Cot la : "+button.getTag(R.id.tagSeatColumn)+"\n Hang la :"+ button.getTag(R.id.tagSeatRow),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                }
                else Toast.makeText(getActivity(), "Khong CO FIlm", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
