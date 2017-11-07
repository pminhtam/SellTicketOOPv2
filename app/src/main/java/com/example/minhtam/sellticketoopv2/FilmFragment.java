package com.example.minhtam.sellticketoopv2;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
    ImageView imgFilmElement;
    TextView txtNameFilmElement;
    Button btnFilmElement;
    public FilmFragment(Context context) {
        this.context = context;
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_film, container, false);
        id = getArguments().getString("id");
        imgFilmElement = (ImageView) view.findViewById(R.id.imgFilmElement);
        txtNameFilmElement = (TextView) view.findViewById(R.id.txtNameFilmElement);
        btnFilmElement = (Button) view.findViewById(R.id.btnFilmElement);
        Toast.makeText(getActivity(), id, Toast.LENGTH_SHORT).show();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new GetFilmElement().execute("https://tickett.herokuapp.com/api/v1/customers/films/" + id);
            }
        });
        btnFilmElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ChooseSeatFragment frag = new ChooseSeatFragment();
                fragmentTransaction.replace(R.id.frame,frag);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    //
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
                    String image = datajson.getString("image");
                    String name = datajson.getString("name");
                    Glide.with(context)
                            .load("https://tickett.herokuapp.com" +image)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imgFilmElement);
                    txtNameFilmElement.setText(name);
                    Toast.makeText(getActivity(), "Xong roi", Toast.LENGTH_SHORT).show();


                }
                else Toast.makeText(getActivity(), "Khong CO FIlm", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    //
}
