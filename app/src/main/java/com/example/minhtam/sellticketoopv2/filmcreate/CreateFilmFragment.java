package com.example.minhtam.sellticketoopv2.filmcreate;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
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
import com.example.minhtam.sellticketoopv2.updateuserinfo.UpdateImageFragment;

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
 * Created by trungdunghoang on 18/11/2017.
 */

public class CreateFilmFragment extends Fragment {

    String token, name, kind, duration, rlDate, description, path;
    EditText editName, editKind, editDuration, editRlDate, editDescription;
    Button btnImport, btnCreate;
    ImageView imgFilm;
    int REQUEST_CODE_FOLDER =1;
    MainActivity activity;
    ProgressDialog progress;
    public void sumbitCreateFilm() {
        verifyStoragePermissions(getActivity());
        name = editName.getText().toString();
        kind = editKind.getText().toString();
        duration = editDuration.getText().toString();
        rlDate = editRlDate.getText().toString();
        description = editDescription.getText().toString();
        new PutFilm().execute(ApiUrl.postCreateFilm());
        progress = new ProgressDialog(activity);
        progress.setMessage("Loading....");
        progress.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_create_film, container, false);
        token = getArguments().getString("token");
        findView(view);


        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmCreateDialog dialog = new ConfirmCreateDialog();
                dialog.setParentFragment(CreateFilmFragment.this);
                dialog.show(activity.getFragmentManager(), "123");
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_CODE_FOLDER && resultCode==RESULT_OK && data!=null){
            Uri uri = data.getData();
            Log.i("info", uri.toString());
            path = getRealPathFromURI(uri);
            try {
                InputStream inputStream = activity.getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgFilm.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getRealPathFromURI( Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };   //hinhf anhr
        Cursor cursor =  activity.managedQuery(contentUri,  proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private String getType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

    }

    class PutFilm extends AsyncTask<String,Void,String> {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
        @Override
        protected String doInBackground(String... strings) {
            File file = new File(path);
            String content_type = getType(file.getPath());
            String file_path = file.getAbsolutePath();


            RequestBody file_body = RequestBody.create(MediaType.parse(content_type),file);
            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("name",name)
                    .addFormDataPart("kind", kind)
                    .addFormDataPart("duration", duration)
                    .addFormDataPart("release_date", rlDate)
                    .addFormDataPart("image",file_path.substring(file_path.lastIndexOf("/")+1),file_body)
                    .setType(MultipartBody.FORM)
                    .build();
            Log.i("info", strings[0]);
            Request request = new Request.Builder()
                    .url(strings[0])
                    .addHeader("Authorization",token)
                    .post(requestBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("info", "err");
            Log.i("info", s);
            super.onPostExecute(s);
            try {
                JSONObject body = new JSONObject(s);
                int code = body.getInt("code");
                if (code == 1) {
                    progress.dismiss();
//                    ((MainActivity) getActivity()).setNavigationDetail();
//                    ((MainActivity) getActivity()).moveToHomeFragment();

                } else Toast.makeText(getActivity(), "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
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

    void findView(View view) {
        editName = (EditText) view.findViewById(R.id.editName);
        editKind = (EditText) view.findViewById(R.id.editKind);
        editDuration = (EditText) view.findViewById(R.id.editDuration);
        editRlDate = (EditText) view.findViewById(R.id.editRlDate);
        editDescription = (EditText) view.findViewById(R.id.editDescription);
        btnImport = (Button) view.findViewById(R.id.btnImport);
        btnCreate = (Button) view.findViewById(R.id.btnCreate);
        imgFilm = (ImageView) view.findViewById(R.id.imgFilm);
    }
}