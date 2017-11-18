package com.example.minhtam.sellticketoopv2;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhtam.sellticketoopv2.home.HomeFragment;

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
    UserDataManager userDataManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof UserDataManager){
            userDataManager = (UserDataManager) context;
        }
        else{
            throw new RuntimeException(context.toString() + " must implement onViewSelected");
        }
    }

    public LogInFragment() {
        // Required empty public constructor
    }

    EditText edtEmailLogIn,edtPasswordLogIn;
    TextView txtCreateAccount;
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
        txtCreateAccount = (TextView) view.findViewById(R.id.txtCreateAccount);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        context = getActivity();

        // Click Create Account
        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((MainActivity) getActivity()).moveToSignInFragment();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                String password = edtPasswordLogIn.getText().toString();
                String email = edtEmailLogIn.getText().toString();
                Toast.makeText(getActivity(), "Đang gửi", Toast.LENGTH_LONG).show();
                request(ApiUrl.signIn(), email, password);
                }
            });
            }
        });

        return view;
    }

    public void request(String url,String email,String password){
        new PostLogIn().execute(url,email,password);
    }

    //class gui request để đăng nhập
    private class PostLogIn extends AsyncTask<String,Integer,String> {
        //API web dang nhap
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
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
                Log.i("info", s);
                JSONObject body = new JSONObject(s);
                int code = body.getInt("code");
                if (code == 1) {
                    userDataManager.setUserData(s);
                    Toast.makeText(getActivity(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    //Đăng nhập thành công thì refresh lại thanh điều hướng
                    ((MainActivity) getActivity()).getUserDataFromToken();
                    ((MainActivity) getActivity()).setNavigationDetail();
                    ((MainActivity) getActivity()).moveToHomeFragment();
                } else Toast.makeText(getActivity(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
