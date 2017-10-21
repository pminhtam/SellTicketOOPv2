package com.example.minhtam.sellticketoopv2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {
    //Đăng ký

    public SignInFragment() {
        // Required empty public constructor
    }
    EditText edtEmailSign,edtPasswordSign;
    Button btnSubmitSign;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        edtEmailSign = (EditText) view.findViewById(R.id.edtEmailSign);
        edtPasswordSign = (EditText) view.findViewById(R.id.edtPasswordSign);
        btnSubmitSign = (Button) view.findViewById(R.id.btnSubmitSign);

        btnSubmitSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmailSign.getText().toString();
                String password = edtPasswordSign.getText().toString();
                Toast.makeText(getActivity(),"Email:"+email+"\n password:"+password,Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }

}
