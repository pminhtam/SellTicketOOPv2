package com.example.minhtam.sellticketoopv2.userhistorybookticket;


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
                        String scheduleId = listHistory.getJSONObject(i).getString("schedule_id");
                        String nameLocation = listHistory.getJSONObject(i).getString("name");
                        items.add(new ItemUserHistoryBookTicket(price,row,column,scheduleId,nameLocation));
                    }
                    rcUserHistoryBookTicket.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
                    rcUserHistoryBookTicket.setLayoutManager(layoutManager);
                    adapter = new UserHistoryAdapter(getActivity(),items);
                    rcUserHistoryBookTicket.setAdapter(adapter);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for(int i=0;i<items.size();i++){
                                if(i<10)
                                    new GetSchedule().execute(ApiUrl.getSchedule(items.get(i).getScheduleId()),"0"+String.valueOf(i));
                                else
                                    new GetSchedule().execute(ApiUrl.getSchedule(items.get(i).getScheduleId()),String.valueOf(i));
                            }
                        }
                    });

                } else Toast.makeText(getActivity(), "thất bại", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class GetSchedule extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            Request request = new Request.Builder()
                    .url(url).addHeader("Authorization",token).build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                return strings[1]+response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        JSONArray seats;
        @Override
        protected void onPostExecute(String s) {
//            Log.i("ChooseSeatFragment",s);
            super.onPostExecute(s);
//            Log.e("History ",s);

            String index = s.substring(0,2);
//            Log.e("History ",index);

            String sub = s.substring(2);
//            Log.e("History ",sub);

            try {
                JSONObject body = new JSONObject(sub);
                int code = body.getInt("code");
                if (code == 1) {
//                    Log.e("History ",index);
                    JSONObject data = body.getJSONObject("data");
                    int i = Integer.parseInt(index);
                    items.get(i).setNameFilm(data.getString("name"));
                    items.get(i).setImage(data.getString("image"));
                    adapter.notifyDataSetChanged();
                } else Toast.makeText(getActivity(), "Khong Co Film", Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
