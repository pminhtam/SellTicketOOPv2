package com.example.minhtam.sellticketoopv2.userhistorybookticket;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class UserHistoryBookTicketFragment extends Fragment {


    public UserHistoryBookTicketFragment() {
        // Required empty public constructor
    }
    String token;

    RecyclerView rcUserHistoryBookTicket;
    ArrayList<ItemUserHistoryBookTicket> items;
    UserHistoryAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_history_book_ticket, container, false);
        token = getArguments().getString("token");
        rcUserHistoryBookTicket = (RecyclerView) view.findViewById(R.id.rcUserHistoryBookTicket);
        items = new ArrayList<>();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new GetHistoryBook().execute(ApiUrl.getUserHistoryBookTicket());
            }
        });
        return view;
    }
    private class GetHistoryBook extends AsyncTask<String,Void,String>{
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
            try {
                JSONObject body = new JSONObject(s);
                int code = body.getInt("code");
                if (code == 1) {
                    JSONArray listHistory = body.getJSONArray("data");

                    for(int i=0;i<listHistory.length();i++){
                        int price = listHistory.getJSONObject(i).getInt("price");
                        String row = listHistory.getJSONObject(i).getString("seat_row");
                        String column = listHistory.getJSONObject(i).getString("seat_col");
                        String nameLocation = listHistory.getJSONObject(i).getString("location_name");
                        String nameFilm = listHistory.getJSONObject(i).getString("film_name");
                        String image = listHistory.getJSONObject(i).getString("film_image");
                        String time_begin = listHistory.getJSONObject(i).getString("time_begin");
                        String time_end = listHistory.getJSONObject(i).getString("time_end");
                        String time_user_book = listHistory.getJSONObject(i).getString("time_user_book");
                        items.add(new ItemUserHistoryBookTicket(price,row,column,nameFilm,nameLocation,image,time_begin,time_end,time_user_book));
                    }
                    rcUserHistoryBookTicket.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
                    rcUserHistoryBookTicket.setLayoutManager(layoutManager);
                    rcUserHistoryBookTicket.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
                    adapter = new UserHistoryAdapter(getActivity(),items);
                    rcUserHistoryBookTicket.setAdapter(adapter);

                } else Toast.makeText(getActivity(), "thất bại", Toast.LENGTH_SHORT).show();
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
