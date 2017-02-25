package com.example.a45556.chatrobot.net;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 45556 on 2017-2-22.
 */

public class HttpData extends AsyncTask<String,Void ,String>{
    private HttpURLConnection httpURLConnection;
    private InputStream input;
    private HttpGetDataListener listener;

    private String urlString;
    public HttpData(String urlString,HttpGetDataListener listener){
        this.urlString = urlString;
        this.listener = listener;
    }

    protected String doInBackground(String... params) {
        try {
            URL url = new URL(urlString);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(8000);
            httpURLConnection.setReadTimeout(8000);
            input = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = reader.readLine())!= null){
                builder.append(line);
            }
            return builder.toString();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (input != null)
                    input.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
            if (httpURLConnection != null)
                httpURLConnection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        listener.getDataUrl(s);
        super.onPostExecute(s);
    }
}
