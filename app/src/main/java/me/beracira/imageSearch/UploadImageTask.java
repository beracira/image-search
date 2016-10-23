package me.beracira.imageSearch;

import android.os.AsyncTask;
import android.text.style.ParagraphStyle;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;


/**
 * Created by beracira on 22/10/16.
 */
public class UploadImageTask extends AsyncTask<InputStream, Void, String> {
    @Override
    protected String doInBackground(InputStream... inputStreams) {

//        Log.d("BG", uri);
//        HttpURLConnection urlConnection = null;
        try {

//            URL url = new URL("http://imagebin.ca/upload.php");
//            urlConnection = (HttpURLConnection) url.openConnection();
//
//            urlConnection.setDoInput(true);
//            urlConnection.setDoOutput(true);
//            urlConnection.setRequestProperty("Connection", "Keep-Alive");
//            urlConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//            urlConnection.setRequestMethod("POST");



//
//            byte[] buffer = new byte[10240];
//            int len;
//            while ((len = fileInputStream.read(buffer)) != -1) {
//                out.write(buffer, 0, len);
//            }
//
//
//            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
//            BufferedReader r = new BufferedReader(new InputStreamReader(in));
//            StringBuilder total = new StringBuilder();
//            String line;
//            while ((line = r.readLine()) != null) {
//                total.append(line).append('\n');
//            }
//            Log.d("output", total.toString());


        } catch (Exception e) {
            Log.d("UP", e.toString());
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }




}
