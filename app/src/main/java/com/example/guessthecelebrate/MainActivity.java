package com.example.guessthecelebrate;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DownloadTask downloadTask=new DownloadTask();
        String result=null;
        try {
           result= downloadTask.execute("http://www.posh24.se/kandisar").get();
            Log.i(TAG, "onCreate: the result is  "+result);
            String[]splitresult=result.split("<div class=\"listedArticles\">");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public class DownloadTask extends AsyncTask<String,Void, String>
    {

String result="";
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This will normally run on a background thread. But to better
         * support testing frameworks, it is recommended that this also tolerates
         * direct execution on the foreground thread, as part of the {@link #execute} call.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param strings The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected String doInBackground(String... urls) {
            try {
                URL url=new URL(urls[0]);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                InputStream inputStream=connection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                int data =inputStreamReader.read();
                while (data!=-1)
                {
                    char current = (char) data;
                    result +=current;
                    data=inputStreamReader.read();

                }
                return result;
            }

            catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
    }
}