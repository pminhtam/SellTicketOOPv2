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
import com.example.minhtam.sellticketoopv2.place.ItemPlace;
import com.example.minhtam.sellticketoopv2.place.PlaceAdapter;

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
public class ChooseLocationFragment extends Fragment {

    ItemChooseFilmSell itemChooseFilm;
    String token;
    public ChooseLocationFragment(ItemChooseFilmSell itemChooseFilm,String token) {
        // Required empty public constructor
        this.itemChooseFilm = itemChooseFilm;
        this.token = token;
    }

    RecyclerView rcChooseLocation;
    ArrayList<ItemPlace> items;
    ChooseLocationAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_location, container, false);
        rcChooseLocation = (RecyclerView) view.findViewById(R.id.rcChooseLocation);

        getActivity().setTitle("Chọn Địa điểm");

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new GetPlaces().execute(ApiUrl.getPlaces("1"));
            }
        });
        return view;
    }
    private class GetPlaces extends AsyncTask<String,Integer,String> {
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            String url = params[0];
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
            try {
                JSONObject body = new JSONObject(s);
//                Toast.makeText(getActivity(),s, Toast.LENGTH_LONG).show();
                if(body !=null) {
                    JSONArray listPlaces = body.getJSONArray("data");
                    items = new ArrayList<ItemPlace>();
                    for (int i = 0; i < listPlaces.length(); i++) {
                        String name = listPlaces.getJSONObject(i).getString("name");
                        Integer id = listPlaces.getJSONObject(i).getInt("id");
                        items.add(new ItemPlace(name, id));
                    }
                    rcChooseLocation.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rcChooseLocation.setLayoutManager(layoutManager);
                    adapter = new ChooseLocationAdapter(getActivity(), items,itemChooseFilm,token);
                    rcChooseLocation.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
