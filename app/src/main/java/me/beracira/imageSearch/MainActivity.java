package me.beracira.imageSearch;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PathPermission;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            Uri uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
            if (uri != null) {
                ImageView imageView = (ImageView) findViewById(R.id.image);
                imageView.setImageURI(uri);

                UploadImageTask fuck = new UploadImageTask();
                String path = getRealPathFromURI(getApplicationContext(), uri);
                FileInputStream fileInputStream = null;
                try {
                    InputStream iStream = getContentResolver().openInputStream(uri);

                    byte[] bytes = getBytes(iStream);
                    Log.d("Byte Size", Integer.toString(bytes.length));
                    RequestParams params = new RequestParams();


//                    TODO: request permission
//                    if (ContextCompat.checkSelfPermission(MainActivity.this,
//                            Manifest.permission.READ_EXTERNAL_STORAGE)
//                            != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(MainActivity.this,
//                                new String[]{Manifest.permission.READ_CONTACTS});
//                    }


                    File myFile = new File(getRealPathFromURI(getApplicationContext(), uri));
                    try {
                        params.put("file", myFile);
                    } catch(Exception e) {
                        Log.d("Main", e.getMessage());
                    }
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.post("http://imagebin.ca/upload.php", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            try {
                                String url = new String(bytes, "UTF-8");
                                url = url.substring(url.indexOf("http"));
                                url = "https://www.google.com/searchbyimage?site=search&sa=X&image_url=" + url;
                                Log.d("output", i + " " + url);
                                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                                intent1.setData(Uri.parse(url));
                                startActivity(intent1);
                            } catch (Exception e) {
                                Log.d("HTTP", e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        }
                    });
                } catch (Exception e) {
                    Log.d("Main", e.getMessage());
                }
            }
        }
    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}
