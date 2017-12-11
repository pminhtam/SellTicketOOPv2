package com.example.minhtam.sellticketoopv2.seller.createschedule;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.minhtam.sellticketoopv2.ApiUrl;
import com.example.minhtam.sellticketoopv2.MainActivity;
import com.example.minhtam.sellticketoopv2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class CreateScheduleFragment extends Fragment {

    ItemChooseFilmSell itemChooseFilm;
    String token;
    public CreateScheduleFragment(ItemChooseFilmSell itemChooseFilm,String token) {
        // Required empty public constructor
        this.itemChooseFilm = itemChooseFilm;
        this.token = token;
    }

    EditText edtTimeBegin,edtTimeEnd,edtPriceVIP,edtPriceNORMAL;
    Button btnSubmitCreateSchedule;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_schedule, container, false);
        findView(view);

        getActivity().setTitle("Tạo lịch chiếu");

        btnSubmitCreateSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostCreateSchedule().execute(ApiUrl.Seller.getCreateSchedule());
            }
        });
        return view;
    }
    private void findView(View view){
        edtTimeBegin = (EditText) view.findViewById(R.id.edtTimeBegin);
        edtTimeEnd = (EditText) view.findViewById(R.id.edtTimeEnd);
        edtPriceVIP = (EditText) view.findViewById(R.id.edtPriceVIP);
        edtPriceNORMAL = (EditText) view.findViewById(R.id.edtPriceNORMAL);
        btnSubmitCreateSchedule = (Button) view.findViewById(R.id.btnSubmitCreateSchedule);
    }
    class PostCreateSchedule extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        @Override
        protected String doInBackground(String... strings) {


            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("film_id",itemChooseFilm.getFilmId())
                    .addFormDataPart("room_id",itemChooseFilm.getRoomId())
                    .addFormDataPart("time_begin",edtTimeBegin.getText().toString())
                    .addFormDataPart("time_end",edtTimeEnd.getText().toString())
                    .addFormDataPart("price_VIP",edtPriceVIP.getText().toString())
                    .addFormDataPart("price_NORMAL",edtPriceNORMAL.getText().toString())
                    .setType(MultipartBody.FORM)
                    .build();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .addHeader("Authorization",token)
                    .post(requestBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
//                e.printStackTrace();
            }
            return "{\"code\":0,\"message\":\"Cập nhật thất bại\"}";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject body = new JSONObject(s);
                int code = body.getInt("code");
                if (code == 1) {
//                    JSONObject dataJson = body.getJSONObject("data");
//                    ((MainActivity) getActivity()).setNewUserData();
                    Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).moveToHomeFragment();

                } else Toast.makeText(getActivity(), "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
//                e.printStackTrace();
                Log.e("CreateScheduleFragment", "Lỗi chuyển Json");

            }
        }
    }
}
