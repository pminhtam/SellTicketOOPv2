package com.example.minhtam.sellticketoopv2.seller.createschedule;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
@SuppressLint("ValidFragment")
public class ChooseRoomFragment extends Fragment {

    ItemChooseFilmSell itemChooseFilm;
    String token;
    public ChooseRoomFragment(ItemChooseFilmSell itemChooseFilm,String token) {
        // Required empty public constructor
        this.itemChooseFilm = itemChooseFilm;
        this.token = token;
    }
    ArrayList<ItemRoom> items;
    RecyclerView rcChooseRoom;
    ChooseRoomAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_room, container, false);
        rcChooseRoom = (RecyclerView) view.findViewById(R.id.rcChooseRoom);

        getActivity().setTitle("Chọn Phòng");


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new GetRoom().execute(ApiUrl.Seller.getRoom(itemChooseFilm.getLocationId()));
            }
        });

        return view;
    }
    private class GetRoom extends AsyncTask<String,Void,String>{
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            Request request = new Request.Builder()                     //request len web
                    .url(url)
                    .addHeader("Authorization",token)
                    .build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string(); //chuoi tra lai s o ham onPostExecute
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            items = new ArrayList<>();
            try {
                JSONObject body = new JSONObject(s);
                if(body !=null) {
                    JSONArray listRoom = body.getJSONArray("data");
                    for (int i = 0; i < listRoom.length(); i++) {
                        String name = listRoom.getJSONObject(i).getString("name");
                        String id = listRoom.getJSONObject(i).getString("id");
                        items.add(new ItemRoom(id,name));
                    }
                    rcChooseRoom.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rcChooseRoom.setLayoutManager(layoutManager);
                    adapter = new ChooseRoomAdapter(getActivity(), items,itemChooseFilm,token);
                    rcChooseRoom.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
