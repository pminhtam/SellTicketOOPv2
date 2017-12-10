package com.example.minhtam.sellticketoopv2.seller;


import android.Manifest;
import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.minhtam.sellticketoopv2.ApiUrl;
import com.example.minhtam.sellticketoopv2.MainActivity;
import com.example.minhtam.sellticketoopv2.R;

import org.json.JSONArray;
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
public class CreateFilmFragment extends Fragment {


    public CreateFilmFragment() {
        // Required empty public constructor
    }
    String token;
    EditText edtNameCreateFilm,edtKindCreateFilm,edtDurationCreateFilm,edtDateCreateFilm,edtContentCreateFilm;
    ImageView imgCreateFilm;
    Button btnImageCreateFilm,btnSubmitCreateFilm;

    int REQUEST_CODE_FOLDER =1;
    String path;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_film, container, false);
        token = getArguments().getString("token");
        findView(view);

        btnImageCreateFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });

        btnSubmitCreateFilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostCreateFilm().execute(ApiUrl.Seller.getCreateFilm());
            }
        });
        return view;
    }
    private void  findView(View view){
        edtNameCreateFilm = (EditText) view.findViewById(R.id.edtNameCreateFilm);
        edtKindCreateFilm = (EditText) view.findViewById(R.id.edtKindCreateFilm);
        edtDurationCreateFilm = (EditText) view.findViewById(R.id.edtDurationCreateFilm);
        edtDateCreateFilm = (EditText) view.findViewById(R.id.edtDateCreateFilm);
        imgCreateFilm = (ImageView) view.findViewById(R.id.imgCreateFilm);
        btnImageCreateFilm = (Button) view.findViewById(R.id.btnImageCreateFilm);
        btnSubmitCreateFilm = (Button) view.findViewById(R.id.btnSubmitCreateFilm);
        edtContentCreateFilm = (EditText) view.findViewById(R.id.edtContentCreateFilm);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE_FOLDER && resultCode==RESULT_OK && data!=null){
            Uri uri = data.getData();
            path = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgCreateFilm.setImageBitmap(bitmap);
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

    class PostCreateFilm extends AsyncTask<String,Void,String> {
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
                        .addFormDataPart("name", edtNameCreateFilm.getText().toString())
                        .addFormDataPart("kind", edtKindCreateFilm.getText().toString())
                        .addFormDataPart("duration", edtDurationCreateFilm.getText().toString())
                        .addFormDataPart("release_date ", edtDateCreateFilm.getText().toString())
                        .addFormDataPart("image", file_path.substring(file_path.lastIndexOf("/") + 1), file_body)
                        .addFormDataPart("content",edtContentCreateFilm.getText().toString())
                        .setType(MultipartBody.FORM)
                        .build();
                Request request = new Request.Builder()
                        .url(strings[0])
                        .addHeader("Authorization", token)
                        .post(requestBody)
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
            Log.e("CreateFilm",s);
            try {
                JSONObject body = new JSONObject(s);
                int code = body.getInt("code");
                Log.e("HistorySell",s);
                if (code == 1) {
                    Log.e("CreateFilm","Tao thanh cong");
                    ((MainActivity) getActivity()).moveToHomeFragment();
                } else Toast.makeText(getActivity(), "thất bại", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
//                e.printStackTrace();
                Log.e("CreateFilmFragment", "Lỗi chuyển Json");
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
