package com.example.minhtam.sellticketoopv2;


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
import android.widget.EditText;
import android.widget.Toast;

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
public class SignInFragment extends Fragment {
    //Đăng ký
    TokenManager tokenManager;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof TokenManager){
            tokenManager = (TokenManager) context;

        }
        else{
            throw new RuntimeException(context.toString() + " must implement onViewSelected");
        }
    }

    public SignInFragment() {
        // Required empty public constructor
    }
    EditText edtEmailSign,edtPasswordSign,edtNameSign;
    Button btnSubmitSign;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        edtEmailSign = (EditText) view.findViewById(R.id.edtEmailSign);
        edtPasswordSign = (EditText) view.findViewById(R.id.edtPasswordSign);
        edtNameSign = (EditText) view.findViewById(R.id.edtNameSign);
        btnSubmitSign = (Button) view.findViewById(R.id.btnSubmitSign);

        btnSubmitSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmailSign.getText().toString();
                String password = edtPasswordSign.getText().toString();
                String name = edtNameSign.getText().toString();

                Toast.makeText(getActivity(), "Email:" + email + "\n password:" + password, Toast.LENGTH_LONG).show();
                new requestPostURL().execute("https://tickett.herokuapp.com/api/v1/customers/sign_up", name, email, password);
            }
        });

        return view;
    }
    //class gui request để đăng ký
    private class requestPostURL extends AsyncTask<String,Integer,String> {
        //API web dang nhap
        OkHttpClient okHttpClient = new OkHttpClient();
        @Override
        protected String doInBackground(String... params) {
            RequestBody requestBody = new MultipartBody.Builder()       // gan header
                    .addFormDataPart("name",params[1])
                    .addFormDataPart("password",params[3])              //cac bien json de gui du lieu len
                    .addFormDataPart("email",params[2])
                    .setType(MultipartBody.FORM)
                    .build();
            Request request = new Request.Builder()                     //request len web
                    .url(params[0])
                    .post(requestBody)
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
                int code = body.getInt("code");
                if (code == 1) {
                    Toast.makeText(getActivity(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    //Đăng nhập thành công thì chuyển sang fragment film
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    LogInFragment frag = new LogInFragment();

                    fragmentTransaction.replace(R.id.frame,frag);
                    fragmentTransaction.commit();

                } else Toast.makeText(getActivity(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    //
}
