package com.example.minhtam.sellticketoopv2;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.minhtam.sellticketoopv2.analyze.AnalyzeFragment;
import com.example.minhtam.sellticketoopv2.chooseseat.ChooseSeatFragment;
import com.example.minhtam.sellticketoopv2.chooseseat.ChooseSeatFragmentDemo;
import com.example.minhtam.sellticketoopv2.home.HomeFragment;
import com.example.minhtam.sellticketoopv2.place.PlaceFragment;
import com.example.minhtam.sellticketoopv2.updateuserinfo.UpdateUserInfoFragment;
import com.example.minhtam.sellticketoopv2.userhistorybookticket.UserHistoryBookTicketFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,UserDataManager {

    private String token;
    private String userData;
    private NavigationView navigationView;

    TextView txtUserName;
    ImageView imgProfile;

    public String userName, userRole, userAvatar;
    public int userMoney;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(int userMoney) {
        this.userMoney = userMoney;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public boolean isCustomer() {
        return this.userRole.equals("customer");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        txtUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtUserName);
        imgProfile = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imgProfile);

        userData = readCache();
        getUserDataFromToken();
        setNavigationDetail();
        if(userData.isEmpty()) {       //kiểm tra đã đăng nhập chưa
            moveToLogInFragment();
        }
        else{
            moveToHomeFragment();
        }
    }
    //////////////////////////////////////////////////////////////
    //************************************************************
    public void moveToLogInFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, new LogInFragment());
        fragmentTransaction.commit();
    }

    public void moveToSignInFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, new SignInFragment());
        fragmentTransaction.commit();
    }

    public void moveToHomeFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        HomeFragment frag = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        frag.setArguments(bundle);
        fragmentTransaction.addToBackStack(frag.getClass().getSimpleName());
        fragmentTransaction.replace(R.id.frame, frag);
        fragmentTransaction.commit();
    }

    public void moveToPlaceFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        PlaceFragment frag = new PlaceFragment();
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        frag.setArguments(bundle);
        fragmentTransaction.addToBackStack(frag.getClass().getSimpleName());
        fragmentTransaction.replace(R.id.frame, frag);
        fragmentTransaction.commit();
    }

    public void moveToAnalyzeFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        AnalyzeFragment frag = new AnalyzeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        bundle.putString("id", "1");
        frag.setArguments(bundle);
        fragmentTransaction.addToBackStack(frag.getClass().getSimpleName());
        fragmentTransaction.replace(R.id.frame, frag);
        fragmentTransaction.commit();
    }
    public void moveToUpdateUserInfoFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        UpdateUserInfoFragment frag = new UpdateUserInfoFragment(userName,userRole,userAvatar,userMoney);
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        frag.setArguments(bundle);
        fragmentTransaction.addToBackStack(frag.getClass().getSimpleName());
        fragmentTransaction.replace(R.id.frame, frag);
        fragmentTransaction.commit();
    }
    public void moveToUserHistoryBookTicketFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        UserHistoryBookTicketFragment frag = new UserHistoryBookTicketFragment();
        Bundle bundle = new Bundle();
        bundle.putString("token", token);
        frag.setArguments(bundle);
        fragmentTransaction.addToBackStack(frag.getClass().getSimpleName());
        fragmentTransaction.replace(R.id.frame, frag);
        fragmentTransaction.commit();
    }

    //*************************************************************
    ///////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //sự kiện click vào item trong menu_navigation
        if (id == R.id.nav_home) {
            moveToHomeFragment();
        } else if(id == R.id.nav_place) {
            moveToPlaceFragment();
        } else if (id == R.id.nav_login) {
            moveToLogInFragment();
        }else if(id==R.id.nav_update_info){
            moveToUpdateUserInfoFragment();
        }else if(id==R.id.nav_user_history){
            moveToUserHistoryBookTicketFragment();
        }
        else if (id == R.id.nav_signout) {
            SignoutDialog myDialog = new SignoutDialog();
            myDialog.show(getFragmentManager(), "123");
        }
        else if (id ==R.id.nav_chooseSeat){     //thử nghiệm
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ChooseSeatFragmentDemo frag = new ChooseSeatFragmentDemo();
            Bundle bundle = new Bundle();
            bundle.putString("token", token);
            bundle.putString("id", "1");
            frag.setArguments(bundle);
            fragmentTransaction.replace(R.id.frame,frag);
            fragmentTransaction.commit();
        }
        else if (id ==R.id.nav_analyze){     //thử nghiệm
            moveToAnalyzeFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //luu token vao trong file
    private void writeCache(String token) {
        File pathCacheDir = getCacheDir();
        String strCacheFIleName = "userdata.cache";
        String strFileContent = token;
        File newCacheFile = new File(pathCacheDir,strCacheFIleName);
        try {
            newCacheFile.createNewFile();
            FileOutputStream foCache = new FileOutputStream(newCacheFile.getAbsolutePath());
            foCache.write(strFileContent.getBytes());
            foCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Đọc cache
    private String readCache() {
        File pathCacheDir = getCacheDir();
        String strCacheFileName = "userdata.cache";
        File newCacheFile = new File(pathCacheDir,strCacheFileName);
        try {
            Scanner sc = new Scanner(newCacheFile);
            String data = "";
            while (sc.hasNext()){
                data+=sc.next();
            }
            sc.close();
            Log.e("data cache ",data);

            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setNavigationDetail() {
        if (userName.equals("")) { //Chua dang nhap
            txtUserName.setText("Hi!!!");
            Glide.with(this).load(R.drawable.user).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgProfile);
            showItem(R.id.nav_login);
            hideItem(R.id.nav_signout);
            hideItem(R.id.nav_analyze);
        } else {
            txtUserName.setText(getUserName());
            Glide.with(this)
                .load(ApiUrl.URL + userAvatar)
                .error(getResources().getDrawable(R.drawable.user))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
            hideItem(R.id.nav_login);
            showItem(R.id.nav_signout);
            if (isCustomer()) {
                hideItem(R.id.nav_analyze);
            } else {
                showItem(R.id.nav_analyze);
            }
        }
    }

    public void getUserDataFromToken() {
        try {
            JSONObject body = new JSONObject(userData);
            token = body.getString("token");
            JSONObject dataJson = body.getJSONObject("data");
            setUserName(dataJson.getString("name"));
            setUserMoney(dataJson.getInt("balance"));
            setUserRole(dataJson.getString("role"));
            setUserAvatar(dataJson.getString("avatar"));
        } catch (Exception e) {
            e.printStackTrace();
            token = "";
            setUserName("");
            setUserMoney(0);
        }

    }
    private void hideItem(int id){
        navigationView.getMenu().findItem(id).setVisible(false);
    }
    private void showItem(int id){
        navigationView.getMenu().findItem(id).setVisible(true);
    }

    @Override
    public void setUserData(String userData) {
        this.userData = userData;
        writeCache(userData);
    }
    public void setNewUserData() {
        JSONObject data = new JSONObject();
        try {
            data.put("name",userName);
            data.put("balance",userMoney);
            data.put("role",userRole);
            data.put("avatar",userAvatar);
            JSONObject userDataJson = new JSONObject();
            userDataJson.put("code",1);
            userDataJson.put("message","Đăng nhập thành công");
            userDataJson.put("token",token);
            userDataJson.put("data",data);
            Log.e("data new ",userDataJson.toString());
            setUserData(userDataJson.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
