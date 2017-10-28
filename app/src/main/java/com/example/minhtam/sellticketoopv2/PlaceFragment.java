package com.example.minhtam.sellticketoopv2;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ThanhDat on 10/27/2017.
 */

public class PlaceFragment extends Fragment {

    String token;
    PlaceAdapter adapter;
    RecyclerView rcPlace;
    Context context;

    public PlaceFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //lấy chuỗi token được truyền từ MainActivity sang
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        token = getArguments().getString("token");
        rcPlace = (RecyclerView) view.findViewById(R.id.rcPlace);
        context = getActivity();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new PlaceFragment.GetPlaces().execute("https://tickett.herokuapp.com/api/v1/customers/locations","1");
            }
        });
        return view;
    }

    private class GetPlaces extends AsyncTask<String,Integer,String> {
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {

            String page = params[1];
            String url = params[0]+"?page="+page;
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
                    ArrayList<ItemPlace> items = new ArrayList<ItemPlace>();
                    for (int i = 0; i < listPlaces.length(); i++) {
                        String name = listPlaces.getJSONObject(i).getString("name");
                        Integer id = listPlaces.getJSONObject(i).getInt("id");
                        items.add(new ItemPlace(name, id));
                    }
                    rcPlace.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    rcPlace.setLayoutManager(layoutManager);
                    adapter = new PlaceAdapter(getActivity(), items);
                    rcPlace.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
