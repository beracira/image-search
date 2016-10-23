package me.beracira.imageSearch;

import android.os.AsyncTask;
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

/**
 * Created by beracira on 22/10/16.
 */
public class UploadImageTask extends AsyncTask<InputStream, Void, String> {
    @Override
    protected String doInBackground(InputStream... params) {

//        Log.d("BG", uri);
        HttpURLConnection urlConnection = null;
        try {
//            FileInputStream fileInputStream = new FileInputStream(new File(uri));

            URL url = new URL("http://imagebin.ca/upload.php");
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");

            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write("file=".getBytes());
            byte[] bytes = getBytes(params[0]);
            Log.d("Byte Size", Integer.toString(bytes.length));
            out.write(bytes);
            out.flush();
            out.close();

//
//            byte[] buffer = new byte[10240];
//            int len;
//            while ((len = fileInputStream.read(buffer)) != -1) {
//                out.write(buffer, 0, len);
//            }


            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            Log.d("output", total.toString());


        } catch (Exception e) {
            Log.d("UP", e.toString());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }


    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}
