package com.example.minhtam.sellticketoopv2.film;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.minhtam.sellticketoopv2.R;
import com.example.minhtam.sellticketoopv2.chooseseat.ChooseSeatFragment;

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
public class FilmFragment extends Fragment {



    Context context;
    String id;
    String token;
    ImageView imgFilmElement;
    TextView txtNameFilmElement;
    ArrayList itemFilmElement;
    RecyclerView rcFilmElement;
    FilmElementAdapter adapter;
    public FilmFragment(Context context) {
        this.context = context;
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_film, container, false);
        itemFilmElement = new ArrayList();
        id = getArguments().getString("id");
        token = getArguments().getString("token");
        imgFilmElement = (ImageView) view.findViewById(R.id.imgFilmElement);
        txtNameFilmElement = (TextView) view.findViewById(R.id.txtNameFilmElement);

        //
        rcFilmElement = (RecyclerView) view.findViewById(R.id.rcFilmElement);
        rcFilmElement.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rcFilmElement.setLayoutManager(layoutManager);
        //

        Log.e("FilmFragment",token);
        Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new GetFilmElement().execute("https://tickett.herokuapp.com/api/v1/customers/films/" + id);
            }
        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("FilmFragment","Chay GetFilmSchedules");
                new GetFilmSchedules().execute("https://tickett.herokuapp.com/api/v1/customers/schedules",id);
            }
        });

        return view;
    }

    //Lay thông tin bộ phim
    class GetFilmElement extends AsyncTask<String,Void,String>{
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            Request request = new Request.Builder()
                    .url(url).build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject body = new JSONObject(s);
                int code = body.getInt("code");
                if (code == 1) {

                    String data = body.getString("data");
                    JSONObject datajson = new JSONObject(data);

                    //lây thông tin về bộ phim
                    String name = datajson.getString("name");
                    String image = datajson.getString("image");
                    String kind = datajson.getString("kind");
                    String duration = datajson.getString("duration");
                    String content = datajson.getString("content");
                    /*
                    Glide.with(context)
                            .load("https://tickett.herokuapp.com" +image)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imgFilmElement);
                    txtNameFilmElement.setText(name);
                    */
                    ItemFilmElementInfo itemFilmElementInfo = new ItemFilmElementInfo(name,image,kind,duration,content);
                    itemFilmElement.add(0,itemFilmElementInfo);
                    adapter = new FilmElementAdapter(context,itemFilmElement,token,getFragmentManager());
                    rcFilmElement.setAdapter(adapter);
                    Toast.makeText(getActivity(), "Xong noi dung film roi", Toast.LENGTH_SHORT).show();


                }
                else Toast.makeText(getActivity(), "Khong CO FIlm", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    //
    // Lấy dữ liệu về địa điểm chiếu
    class GetFilmSchedules extends AsyncTask<String,Void,String>{
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected String doInBackground(String... strings) {
            String film_id = strings[1];
            String url = strings[0] + "?film_id="+film_id;
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject body = new JSONObject(s);
                int code = body.getInt("code");
                if (code == 1) {
                    Log.e("FilmFragment","Chay GetFilmSchedules xong roi nhé");
                    JSONArray listSchedule = body.getJSONArray("data");
//                    ArrayList<ItemFilmSchedules> itemFilmSchedules = new ArrayList<>();
//                    final ArrayList<String> strItem = new ArrayList<String>();

                    for (int i=0;i<listSchedule.length();i++){
                        String id = listSchedule.getJSONObject(i).getString("id").toString();
                        ItemFilmSchedules itemFilmSchedules = new ItemFilmSchedules(id);
                        itemFilmElement.add(itemFilmSchedules);
                        adapter.notifyDataSetChanged();
//                        strItem.add(id);
                    }
                    /*
                    ArrayAdapter adapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,strItem);
                    lvFilmElement.setAdapter(adapter);
                    lvFilmElement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            ChooseSeatFragment frag = new ChooseSeatFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("id",strItem.get(position));
                            bundle.putString("token",token);
                            frag.setArguments(bundle);
                            fragmentTransaction.replace(R.id.frame,frag);
                            fragmentTransaction.commit();

                        }
                    });
                    */


                    Toast.makeText(getActivity(), "Co Lít View", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getActivity(), "Khong CO FIlm", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
