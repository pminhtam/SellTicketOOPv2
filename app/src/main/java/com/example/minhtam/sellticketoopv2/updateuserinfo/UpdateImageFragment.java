package com.example.minhtam.sellticketoopv2.updateuserinfo;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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

import org.json.JSONException;
import org.json.JSONObject;

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
public class UpdateImageFragment extends Fragment {

    String userName,userAvatar;
    String token;
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

    @SuppressLint("ValidFragment")
    public UpdateImageFragment(String userName,String userAvatar,String token) {
        // Required empty public constructor
        this.userAvatar = userAvatar;
        this.userName = userName;
        this.token = token;

    }
    ImageView imgUpdateImage;
    Button btnChooseUpdateImage,btnSubmitUpdateImage;
    int REQUEST_CODE_FOLDER =1;
    String path;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_image, container, false);

        getActivity().setTitle("Đổi ảnh đại diện");


        imgUpdateImage = (ImageView) view.findViewById(R.id.imgUpdateImage);
        btnChooseUpdateImage = (Button) view.findViewById(R.id.btnChooseUpdateImage);
        btnSubmitUpdateImage = (Button) view.findViewById(R.id.btnSubmitUpdateImage);
        Glide.with(this)
                .load(ApiUrl.URL + userAvatar)
                .error(getResources().getDrawable(R.drawable.user))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgUpdateImage);
        btnChooseUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });
        btnSubmitUpdateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyStoragePermissions(getActivity());
                new PutImage().execute(ApiUrl.getUpdateUserInfo());
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE_FOLDER && resultCode==RESULT_OK && data!=null){
            Uri uri = data.getData();
            path = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgUpdateImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getRealPathFromURI( Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };   //hinhf anhr
        Cursor cursor = getActivity().managedQuery(contentUri,  proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    class PutImage extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        @Override
        protected String doInBackground(String... strings) {
            if(path.length()>2) {
                File file = new File(path);
                String content_type = getType(file.getPath());
                String file_path = file.getAbsolutePath();


                RequestBody file_body = RequestBody.create(MediaType.parse(content_type), file);
                RequestBody requestBody = new MultipartBody.Builder()
                        .addFormDataPart("name", userName)
                        .addFormDataPart("avatar", file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                        .setType(MultipartBody.FORM)
                        .build();
                Request request = new Request.Builder()
                        .url(strings[0])
                        .addHeader("Authorization", token)
                        .put(requestBody)
                        .build();

                try {
                    Response response = okHttpClient.newCall(request).execute();
                    return response.body().string();
                } catch (IOException e) {
//                    e.printStackTrace();
                }
                return "{\"code\":0,\"message\":\"Cập nhật thất bại\"}";
            }
            return "{\"code\":0,\"message\":\"Cập nhật thất bại\"}";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject body = new JSONObject(s);
                int code = body.getInt("code");
                if (code == 1) {
                    JSONObject dataJson = body.getJSONObject("data");
                    String useravatar = dataJson.getString("avatar");
                    activity.setUserAvatar(useravatar);
                    Toast.makeText(getActivity(), "Cập nhập thành công", Toast.LENGTH_SHORT).show();
//                    ((MainActivity) getActivity()).setNewUserData();
                    activity.setNewUserData();
                    ((MainActivity) getActivity()).setNavigationDetail();
                    ((MainActivity) getActivity()).moveToUpdateUserInfoFragment();

                } else Toast.makeText(getActivity(), "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
//                e.printStackTrace();
                Log.e("UpdateImageFragment", "Lỗi chuyển Json");

            }
        }
    }

    private String getType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }
    ///////////
    /**
     * Xin cấp quyền đọc ổ đĩa để load ảnh
     */
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    ////////////////////////////////////////
}
