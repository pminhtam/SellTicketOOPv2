package com.example.minhtam.sellticketoopv2.seller.createschedule;


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
import com.example.minhtam.sellticketoopv2.home.FilmAdapter;

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
public class ChooseFilmSellFragment extends Fragment {


    public ChooseFilmSellFragment() {
        // Required empty public constructor
    }
    String token;
    RecyclerView rcChooseFilmSell;
    ChooseFilmSellAdapter adapter;
    ArrayList<ItemChooseFilmSell> items;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choose_film_sell, container, false);
        token = getArguments().getString("token");
        rcChooseFilmSell = (RecyclerView) view.findViewById(R.id.rcChooseFilmSell);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new GetChooseFilmSell().execute(ApiUrl.getFilms("1"));
            }
        });
        return view;
    }

    private class GetChooseFilmSell extends AsyncTask<String,Integer,String> {
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
                    items = new ArrayList<>();
                    for(int i=0;i<listfilms.length();i++){
                        String id = listfilms.getJSONObject(i).getString("id").toString();
                        String name = listfilms.getJSONObject(i).getString("name").toString();
                        String image = listfilms.getJSONObject(i).getString("image").toString();
                        items.add(new ItemChooseFilmSell(id,name,image));
                    }

                    rcChooseFilmSell.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    rcChooseFilmSell.setLayoutManager(layoutManager);
                    rcChooseFilmSell.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
                    adapter = new ChooseFilmSellAdapter(getActivity(), items,token);
                    rcChooseFilmSell.setAdapter(adapter);

//                Toast.makeText(FilmActivity.this,s,Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
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
