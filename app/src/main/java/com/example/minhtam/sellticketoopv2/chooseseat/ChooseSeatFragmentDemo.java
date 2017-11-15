package com.example.minhtam.sellticketoopv2.chooseseat;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhtam.sellticketoopv2.ApiUrl;
import com.example.minhtam.sellticketoopv2.MainActivity;
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
 * Created by trungdunghoang on 14/11/2017.
 */

public class ChooseSeatFragmentDemo extends Fragment{
    String id;
    String token;
    TextView txtInfo;
    GridView gridView;
    Button btnBook;
    SeatAdapter seatsAdapter;
    ArrayList<ItemSeat> items;

    public ChooseSeatFragmentDemo() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        txtInfo = (TextView) view.findViewById(R.id.txtInfo);
        gridView = (GridView) view.findViewById(R.id.gridView);
        btnBook = (Button) view.findViewById(R.id.btnBook);
        id = getArguments().getString("id");
        token = getArguments().getString("token");
        btnBook.setOnClickListener(new View.OnClickListener() {
            int totalMoney = 0;
            @Override
            public void onClick(View view) {
                final ArrayList<Integer> selectedSeats = seatsAdapter.getSelectedSeats();
                MainActivity activity = (MainActivity)getActivity();
                for (Integer selectedId : selectedSeats) {
                    totalMoney += items.get(selectedId).getPrice();
                }
//                if (totalMoney > Integer.valueOf(activity.getUserMoney())) {
                if (true) {
                    BookFailDialog dialog = new BookFailDialog();
                    dialog.show(activity.getFragmentManager(), "123");
//                    for (Integer selectedId : selectedSeats) {
//                        seatsAdapter.removeSelectedItem(selectedId);
//                    }
                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (Integer selectedId : selectedSeats) {
                                new ChooseSeatFragmentDemo.BookSeat().execute(ApiUrl.bookTicket(""+selectedId));
                            }

                        }
                    });
                }
            }
        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ChooseSeatFragmentDemo.GetSeat().execute(ApiUrl.getSchedule(id));
            }
        });
        return view;
    }

    //Lay thông tin ghế
    class GetSeat extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
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
        JSONArray seats;
        @Override
        protected void onPostExecute(String s) {
            Log.i("ChooseSeatFragment",s);
            super.onPostExecute(s);
            try {
                JSONObject body = new JSONObject(s);
                int code = body.getInt("code");
                if (code == 1) {
                    JSONObject data = body.getJSONObject("data");
                    txtInfo.setText(data.getString("name"));
                    seats = data.getJSONArray("seats");
                    items = new ArrayList<ItemSeat>();
                    for (int i = 0; i < seats.length(); i++) {
                        String row = seats.getJSONArray(i).getString(0).toString();
                        String col = seats.getJSONArray(i).getString(1).toString();
                        String level = seats.getJSONArray(i).getString(2).toString();
                        String state = seats.getJSONArray(i).getString(3).toString();
                        String price = seats.getJSONArray(i).getString(4).toString();
                        String seat_id = seats.getJSONArray(i).getString(5).toString();
                        items.add(new ItemSeat(row, col, Boolean.valueOf(state), level, Integer.valueOf(price), seat_id));
                    }
                    seatsAdapter = new SeatAdapter(getActivity(), items);
                    gridView.setAdapter(seatsAdapter);
                } else Toast.makeText(getActivity(), "Khong Co Film", Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class BookSeat extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient();

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
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
        JSONArray seats;
        @Override
        protected void onPostExecute(String s) {
            Log.i("info","ChooseSeatFragment.BookSeat:"+s);
            super.onPostExecute(s);
            try {
                JSONObject body = new JSONObject(s);
                int code = body.getInt("code");
                if (code == 1) {

                } else Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}