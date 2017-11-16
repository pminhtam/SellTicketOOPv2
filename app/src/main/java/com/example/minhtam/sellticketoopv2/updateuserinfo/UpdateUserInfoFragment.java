package com.example.minhtam.sellticketoopv2.updateuserinfo;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.minhtam.sellticketoopv2.ApiUrl;
import com.example.minhtam.sellticketoopv2.MainActivity;
import com.example.minhtam.sellticketoopv2.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class UpdateUserInfoFragment extends Fragment {

    String userName,userRole,userAvatar;
    int userMoney;
    @SuppressLint("ValidFragment")
    public UpdateUserInfoFragment(String userName, String userRole, String userAvatar, int userMoney) {
        // Required empty public constructor
        this.userName = userName;
        this.userRole = userRole;
        this.userAvatar = userAvatar;
        this.userMoney = userMoney;
    }
    String token;
    ImageView imgUserInfo;              //hiển thị anahr đại diện
    Button btnImageUpdateUserInfo;      //nhấn vào chuyển đến màn hình thay đổi ảnh đại diện
    TextView txtUserNameInfo;           //Hiển thị tên
    TextView txtNameUpdateUserInfo;     //nhấn vào để thay đổi tên
    TextView txtUserRoleInfo;           //loại tài khoản
    TextView txtUserBalanceInfo;        //số dư tài khoản
    TextView txtPasswordUpdateUserInfo; //nhấn vào để cập nhật mật khẩu
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_user_info, container, false);
        token = getArguments().getString("token");
        findView(view);
        showInfo();
        btnImageUpdateUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                UpdateImageFragment frag = new UpdateImageFragment(userName,userAvatar,token);
                fragmentTransaction.replace(R.id.frame, frag);
                fragmentTransaction.commit();
            }
        });
        txtNameUpdateUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                UpdateNameFragment frag = new UpdateNameFragment(userName,token);
                fragmentTransaction.replace(R.id.frame, frag);
                fragmentTransaction.commit();
            }
        });
        txtPasswordUpdateUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                UpdatePasswordFragment frag = new UpdatePasswordFragment(token);
                fragmentTransaction.replace(R.id.frame, frag);
                fragmentTransaction.commit();
            }
        });

        return view;
    }


    void findView(View view){
        imgUserInfo = (ImageView) view.findViewById(R.id.imgUserInfo);
        btnImageUpdateUserInfo = (Button) view.findViewById(R.id.btnImageUpdateUserInfo);
        txtUserNameInfo = (TextView) view.findViewById(R.id.txtUserNameInfo);
        txtNameUpdateUserInfo = (TextView) view.findViewById(R.id.txtNameUpdateUserInfo);
        txtUserRoleInfo = (TextView) view.findViewById(R.id.txtUserRoleInfo);
        txtUserBalanceInfo = (TextView) view.findViewById(R.id.txtUserBalanceInfo);
        txtPasswordUpdateUserInfo = (TextView) view.findViewById(R.id.txtPasswordUpdateUserInfo);
    }
    void showInfo(){
        Glide.with(this)
                .load(ApiUrl.URL + userAvatar)
                .error(getResources().getDrawable(R.drawable.user))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgUserInfo);
        txtUserNameInfo.setText(userName);
        txtUserRoleInfo.setText(userRole);
        txtUserBalanceInfo.setText(String.valueOf(userMoney));
    }

}
