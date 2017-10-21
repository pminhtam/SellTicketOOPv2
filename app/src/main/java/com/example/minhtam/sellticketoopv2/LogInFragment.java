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
public class LogInFragment extends Fragment {

    //class đăng nhập
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

    public LogInFragment() {
        // Required empty public constructor
    }

    EditText edtEmailLogIn,edtPasswordLogIn;
    Button btnSubmit;
    String token;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        edtEmailLogIn = (EditText) view.findViewById(R.id.edtEmailLogIn);
        edtPasswordLogIn = (EditText) view.findViewById(R.id.edtPasswordLogIn);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        context = getActivity();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String password = edtPasswordLogIn.getText().toString();
                        String email = edtEmailLogIn.getText().toString();
                        Toast.makeText(getActivity(),"dang chay",Toast.LENGTH_LONG).show();
                        //API web dang nhap
                        new requestPostURL().execute("https://tickett.herokuapp.com/api/v1/customers/sign_in",email,password);
                    }
                });
            }
        });

        return view;
    }
    //class gui request để đăng nhập
    private class requestPostURL extends AsyncTask<String,Integer,String> {
        //API web dang nhap
        OkHttpClient okHttpClient = new OkHttpClient();
        @Override
        protected String doInBackground(String... params) {
            RequestBody requestBody = new MultipartBody.Builder()       // gan header
                    .addFormDataPart("password",params[2])              //cac bien json de gui du lieu len
                    .addFormDataPart("email",params[1])
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
                    token = body.getString("token");

                    tokenManager.setToken(token);
                    Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                    //Đăng nhập thành công thì chuyển sang fragment film
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    HomeFragment frag = new HomeFragment();
                    //Truyền chuỗi token cho frag
                    Bundle bundle = new Bundle();
                    bundle.putString("token",token);
                    frag.setArguments(bundle);
                    //Thay đổi fragment hiển thị
                    fragmentTransaction.replace(R.id.frame,frag);
                    fragmentTransaction.commit();

                } else Toast.makeText(getActivity(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    //
}
