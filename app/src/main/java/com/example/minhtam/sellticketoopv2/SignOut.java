package com.example.minhtam.sellticketoopv2;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Minh Tam on 10/21/2017.
 */

public class SignOut {
    // Đăng xuất
    private Context context;
    TokenManager tokenManager;
    FragmentManager fragmentManager;
    public SignOut(Context context,FragmentManager fragmentManager){
        this.context = context;
        this.fragmentManager = fragmentManager;
        DeleteCache();

    }
    private void DeleteCache(){
        tokenManager = (TokenManager) context;
        tokenManager.setToken("");          //xóa token
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LogInFragment frag = new LogInFragment();
        fragmentTransaction.replace(R.id.frame,frag);
        fragmentTransaction.commit();

    }
}
