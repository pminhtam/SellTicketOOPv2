package com.example.minhtam.sellticketoopv2.home;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
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
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    String token;
    FilmAdapter adapter;
    RecyclerView rcFilm;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //lấy chuỗi token được truyền từ MainActivity sang
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        token = getArguments().getString("token");
        rcFilm = (RecyclerView) view.findViewById(R.id.rcFilm);
        context = getActivity();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
            new GetFilm().execute(ApiUrl.getFilms("1"));
            }
        });
        return view;
    }
    private class GetFilm extends AsyncTask<String,Integer,String> {
        //API web dang nhap
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();

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

            super.onPostExecute(s);

            try {
                JSONObject body = new JSONObject(s);
//                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
                if(body !=null) {
                    JSONArray listfilms = body.getJSONArray("data");
                    // Phan header
                    ArrayList<ArrayList<ItemFilm>> itemsALL = new ArrayList<ArrayList<ItemFilm>>();
                    ArrayList<ItemFilm> items = new ArrayList<ItemFilm>();
                    for (int i = 0; i < 3; i++) {
                        String id = listfilms.getJSONObject(i).getString("id").toString();
                        String name = listfilms.getJSONObject(i).getString("name").toString();
                        String kind = listfilms.getJSONObject(i).getString("kind").toString();
                        String image = listfilms.getJSONObject(i).getString("image").toString();
                        items.add(new ItemFilm(id,name, image, kind));
                    }
                    itemsALL.add(items);
//                    items.clear();
                    //
                    ArrayList<ItemFilm> item0 = new ArrayList<ItemFilm>();
                    ArrayList<ItemFilm> item1 = new ArrayList<ItemFilm>();
                    ArrayList<ItemFilm> item2 = new ArrayList<ItemFilm>();
                    ArrayList<ItemFilm> item3 = new ArrayList<ItemFilm>();
                    for (int i = 3; i < listfilms.length(); i++) {
                        String id = listfilms.getJSONObject(i).getString("id").toString();
                        String name = listfilms.getJSONObject(i).getString("name").toString();
                        String kind = listfilms.getJSONObject(i).getString("kind").toString();
                        String image = listfilms.getJSONObject(i).getString("image").toString();
                        if(i%4==0){
                            item0.add(new ItemFilm(id,name, image, kind));
                        }
                        else if(i%4==1){
                            item1.add(new ItemFilm(id,name, image, kind));
                        }
                        else if(i%4==2){
                            item2.add(new ItemFilm(id,name, image, kind));
                        }
                        else if(i%4==3){
                            item3.add(new ItemFilm(id,name, image, kind));
                        }
                    }
                    itemsALL.add(item0);
                    itemsALL.add(item1);
                    itemsALL.add(item2);
                    itemsALL.add(item3);
//                    item0.clear();
//                    item1.clear();
//                    item2.clear();
//                    item3.clear();
//                    String txt = "item 0 la "+String.valueOf(itemsALL.get(0).size())+"\n item 1 la "+ String.valueOf(itemsALL.get(1).size())+"\n item2 la "+String.valueOf(itemsALL.get(2).size());
//                Toast.makeText(getActivity(),txt,Toast.LENGTH_LONG).show();

                    rcFilm.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    rcFilm.setLayoutManager(layoutManager);
                    rcFilm.addItemDecoration(new SimpleDividerItemDecoration(context));
                    adapter = new FilmAdapter(getActivity(), itemsALL,getFragmentManager(),token);
                    rcFilm.setAdapter(adapter);

//                Toast.makeText(FilmActivity.this,s,Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

}
