package com.example.minhtam.sellticketoopv2.place;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minhtam.sellticketoopv2.ApiUrl;
import com.example.minhtam.sellticketoopv2.R;
import com.example.minhtam.sellticketoopv2.home.ItemFilm;

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

public class FilmPlaceFragment extends Fragment {

    String token;
    String location_id;
    FilmPlaceAdapter adapter;
    RecyclerView rcPlace;
    Context context;

    public FilmPlaceFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //lấy chuỗi token được truyền từ MainActivity sang
        View view = inflater.inflate(R.layout.fragment_film_place, container, false);
        token = getArguments().getString("token");
        location_id = getArguments().getString("id");
        rcPlace = (RecyclerView) view.findViewById(R.id.rcFilmPlace);
        context = getActivity();


        getActivity().setTitle("Rạp chiếu");

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
            new FilmPlaceFragment.GetFilmPlaces().execute(ApiUrl.getPlaceSchedules(location_id, "1"));
            }
        });
        return view;
    }

    private class GetFilmPlaces extends AsyncTask<String,Integer,String> {
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
                Log.i("info", s);
//                Toast.makeText(getActivity(),s, Toast.LENGTH_LONG).show();
                if(body !=null) {
                    JSONArray listFilmPlaces = body.getJSONArray("data");
                    ArrayList<ItemFilm> items = new ArrayList<ItemFilm>();
                    for (int i = 0; i < listFilmPlaces.length(); i++) {
                        String id = listFilmPlaces.getJSONObject(i).getString("film_id").toString();
                        String name = listFilmPlaces.getJSONObject(i).getString("film_name").toString();
                        String kind = listFilmPlaces.getJSONObject(i).getString("film_kind").toString();
                        String image = listFilmPlaces.getJSONObject(i).getString("film_image").toString();
                        items.add(new ItemFilm(id,name, image, kind));
                        items.add(new ItemFilm(id,name, image, kind));
                        items.add(new ItemFilm(id,name, image, kind));
                    }
                    rcPlace.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    rcPlace.setLayoutManager(layoutManager);
                    adapter = new FilmPlaceAdapter(getActivity(), items);
                    rcPlace.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
