package org.kasapbasi.week09001;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    class DownloadContent extends AsyncTask<String,Void,String>{


        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
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

OkHttpClient client = new OkHttpClient();
        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                return response.body().string();
            }
        }

        @Override
        protected String doInBackground(String... strings) {


            String run= null;
            try {
                run = run(strings[0]);
            }
            catch (Exception ex){
                run=null;
            }
            return run;


        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         *
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param s The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("HAVA_CiVA",s);

            try {
                JSONObject obj= new JSONObject(s);
                String name= obj.getString("name");
                JSONObject main= obj.getJSONObject("main");
                Double temp= main.getDouble("temp");

                 temp=temp-273;

                tv.setText(name +"\n" +temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    tv= (TextView) findViewById(R.id.tvTemp);

        DownloadContent dc= new DownloadContent();
       Log.i("HAVA", String.valueOf(dc.execute("https://api.openweathermap.org/data/2.5/weather?q=Elazig&APPID=")));


    }
}
