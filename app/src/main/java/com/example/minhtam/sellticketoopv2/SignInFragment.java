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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
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

    public SignInFragment() {
        // Required empty public constructor
    }
    EditText edtEmailSign,edtPasswordSign,edtNameSign;
    TextView txtLogIn;
    Spinner spinnerRoles;
    Button btnSubmitSign;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        edtEmailSign = (EditText) view.findViewById(R.id.edtEmailSign);
        edtPasswordSign = (EditText) view.findViewById(R.id.edtPasswordSign);
        edtNameSign = (EditText) view.findViewById(R.id.edtNameSign);
        spinnerRoles = (Spinner) view.findViewById(R.id.spinnerRoles);
        txtLogIn = (TextView) view.findViewById(R.id.txtLogIn);
        btnSubmitSign = (Button) view.findViewById(R.id.btnSubmitSign);

        // Init spinner roles
        ArrayAdapter<CharSequence> adapterRoles = ArrayAdapter.createFromResource(getActivity(),
            R.array.spinner_roles_array, android.R.layout.simple_spinner_item);
        adapterRoles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoles.setAdapter(adapterRoles);

        // Click Log In
        txtLogIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, new LogInFragment());
                fragmentTransaction.commit();
            }
        });

        btnSubmitSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmailSign.getText().toString();
                String password = edtPasswordSign.getText().toString();
                String name = edtNameSign.getText().toString();
                String role = spinnerRoles.getSelectedItem().toString();
                if (role.equals("Người bán vé")) role = "seller";
                else role = "customer";

                Toast.makeText(getActivity(), "Đang gửi", Toast.LENGTH_SHORT).show();
                new PostSignIn().execute(ApiUrl.signUp(), name, email, password, role);
            }
        });

        return view;
    }

    //class gui request để đăng ký
    private class PostSignIn extends AsyncTask<String,Integer,String> {
        //API web dang nhap
        OkHttpClient okHttpClient = new OkHttpClient();
        @Override
        protected String doInBackground(String... params) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("name", params[1])
                    .addFormDataPart("email", params[2])
                    .addFormDataPart("password", params[3])
                    .addFormDataPart("role", params[4])
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
                Toast.makeText(getActivity(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
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
                    //Đăng ky thành công thì chuyển sang fragment login
                    ((MainActivity) getActivity()).moveToLogInFragment();
                } else Toast.makeText(getActivity(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
