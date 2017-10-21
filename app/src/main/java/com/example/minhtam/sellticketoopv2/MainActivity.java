package com.example.minhtam.sellticketoopv2;

import android.content.ClipData;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,TokenManager {

    String token = "";
    NavigationView nav_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        token = readCache();
        if(token.isEmpty()) {       //kiểm tra đã đăng nhập chưa
            //nếu chưa vào màn hình đăng nhập
            // Khoi tao LogIn thêm vào màn hình chính
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LogInFragment frag = new LogInFragment();
            //Thay đổi fragment hiển thị
            fragmentTransaction.add(R.id.frame, frag);
            fragmentTransaction.commit();

        }
        else{
            //nếu đã đăng nhập chuyển đến trang chủ
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            HomeFragment frag = new HomeFragment();
            //Truyền chuỗi token cho frag
            Bundle bundle = new Bundle();
            bundle.putString("token",token);
            frag.setArguments(bundle);
            //Thay đổi fragment hiển thị
            fragmentTransaction.replace(R.id.frame,frag);
            fragmentTransaction.commit();

            //khi đã đăng nhập ẩn cái item đăng nhập trên menu đi
            hideItem(R.id.nav_login);
            hideItem(R.id.nav_signin);

        }

    }

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
            // Handle the camera action
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            HomeFragment frag = new HomeFragment();
            //Truyền chuỗi token cho frag bằng Bundle
            Bundle bundle = new Bundle();
            bundle.putString("token",token);
            frag.setArguments(bundle);
            //
            //Thay đổi fragment hiển thị
            fragmentTransaction.replace(R.id.frame,frag);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_login) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LogInFragment frag = new LogInFragment();
            fragmentTransaction.replace(R.id.frame,frag);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_signin) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            SignInFragment frag = new SignInFragment();
            //Truyền chuỗi token cho frag bằng Bundle
            Bundle bundle = new Bundle();
            bundle.putString("token",token);
            frag.setArguments(bundle);
            //
            fragmentTransaction.replace(R.id.frame,frag);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
        writeCache(token);
    }
    //luu token vao trong file
    private void writeCache(String token) {
        File pathCacheDir = getCacheDir();
        String strCacheFIleName = "token.cache";
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
        String strCacheFileName = "token.cache";
        File newCacheFile = new File(pathCacheDir,strCacheFileName);
        try {
            Scanner sc = new Scanner(newCacheFile);
            String data = "";
            while (sc.hasNext()){
                data+=sc.next();
            }
            sc.close();
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
    private void hideItem(int id){
        nav_view = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = nav_view.getMenu();
        nav_Menu.findItem(id).setVisible(false);
    }
}
