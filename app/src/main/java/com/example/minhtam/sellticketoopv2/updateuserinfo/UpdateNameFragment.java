package com.example.minhtam.sellticketoopv2.updateuserinfo;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhtam.sellticketoopv2.ApiUrl;
import com.example.minhtam.sellticketoopv2.MainActivity;
import com.example.minhtam.sellticketoopv2.R;
import com.example.minhtam.sellticketoopv2.UserDataManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */

@SuppressLint("ValidFragment")
public class UpdateNameFragment extends Fragment {

    String userName,token;
    @SuppressLint("ValidFragment")
    public UpdateNameFragment(String userName, String token) {
        // Required empty public constructor
        this.userName = userName;
        this.token = token;
    }

    EditText edtUpdateName;
    Button btnSubmitUpdateName;
    MainActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity){
            activity = (MainActivity) context;
        }
        else{
            throw new RuntimeException(context.toString() + " must implement onViewSelected");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_name, container, false);
        getActivity().setTitle("Đổi tên");

        findView(view);
        btnSubmitUpdateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newname = edtUpdateName.getText().toString();
                activity.setUserName(newname);
                activity.setNavigationDetail();
                new PutName().execute(ApiUrl.getUpdateUserInfo(),newname);
            }
        });
        return view;
    }
    void findView(View view){
        edtUpdateName = (EditText) view.findViewById(R.id.edtUpdateName);
        btnSubmitUpdateName = (Button) view.findViewById(R.id.btnSubmitUpdateName);
    }


    class PutName extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        @Override
        protected String doInBackground(String... strings) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("name",strings[1])
                    .addFormDataPart("avatar","")
                    .setType(MultipartBody.FORM)
                    .build();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .addHeader("Authorization",token)
                    .put(requestBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
//                e.printStackTrace();
            }
            return "{\"code\":0,\"message\":\"Thất bại\"}";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject body = new JSONObject(s);
                int code = body.getInt("code");
                if (code == 1) {
//                    Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
                    activity.setNewUserData();
                    Toast.makeText(getActivity(), "đổi tên thành công", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).moveToUpdateUserInfoFragment();
                }
                else{
                    Toast.makeText(getActivity(), "thất bại", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
//                e.printStackTrace();
                Log.e("UpdateNameFragment", "Lỗi chuyển Json");

            }
//            activity.moveToHomeFragment();
        }
    }

}
