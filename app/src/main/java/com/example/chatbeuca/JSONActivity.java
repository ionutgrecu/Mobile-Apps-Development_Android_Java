package com.example.chatbeuca;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class JSONActivity extends ListActivity {

    private ProgressDialog pDialog;

    JSONArray movies = null;

    ArrayList<HashMap<String, String>> movieList;

    public static final String TAG_MOVIES = "movies";
    public static final String TAG_ID = "id";
    public static final String TAG_TITLE = "title";
    public static final String TAG_DURATION = "duration";
    public static final String TAG_RELEASE = "release";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        movieList = new ArrayList<HashMap<String, String>>();

        URL url = null;
        try {
            url = new URL("http://movio.biblacad.ro/movies.json");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        GetMovies m = new GetMovies();
        m.setOnTaskFinishedEvent(new OnTaskExecutionFinished() {
            @Override
            public void onTaskFinishedEvent(String result) {

                if(pDialog.isShowing())
                {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pDialog.dismiss();
                }

                ListAdapter adapter = new SimpleAdapter(JSONActivity.this,
                        movieList,
                        R.layout.list_item,
                        new String[]{TAG_TITLE, TAG_RELEASE, TAG_DURATION},
                        new int[]{R.id.title, R.id.release, R.id.duration}){

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);

                        HashMap<String, String> currentRow = movieList.get(position);

                        TextView duration = view.findViewById(R.id.duration);
                        int valDuration = Integer.parseInt(currentRow.get(TAG_DURATION));
                        if(valDuration < 100)
                            duration.setTextColor(Color.RED);
                        else
                            duration.setTextColor(Color.GREEN);

                        return view;
                    }
                };

                setListAdapter(adapter);

            }
        });
        m.execute(url);
    }

    public interface OnTaskExecutionFinished
    {
        void onTaskFinishedEvent(String result);
    }

    public class GetMovies extends AsyncTask<URL, Void, String>
    {
        private OnTaskExecutionFinished event;

        public void setOnTaskFinishedEvent(OnTaskExecutionFinished event)
        {
            if (event!=null)
                this.event = event;
        }

        @Override
        protected String doInBackground(URL... urls) {
            HttpURLConnection conn = null;

            try {
                conn = (HttpURLConnection) urls[0].openConnection();
                conn.setRequestMethod("GET");
                InputStream ist = conn.getInputStream();

                InputStreamReader isr = new InputStreamReader(ist);
                BufferedReader br = new BufferedReader(isr);
                String linie = "";
                String sbuf = "";
                while ((linie = br.readLine()) != null) {
                    sbuf += linie + "\n";
                }

                loadJSONObject(sbuf);

                return sbuf;

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null)
                    conn.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (this.event!=null)
                this.event.onTaskFinishedEvent(s);
            else
                Log.e("GetMovies", "event is null");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(JSONActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        public void loadJSONObject(String jsonStr)
        {
            if(jsonStr!=null)
            {
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(jsonStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    movies = jsonObj.getJSONArray(TAG_MOVIES);

                    for(int i=0;i<movies.length();i++)
                    {
                        JSONObject c = movies.getJSONObject(i);
                        String id = c.getString(TAG_ID);
                        String title = c.getString(TAG_TITLE);
                        String duration = c.getString(TAG_DURATION);
                        String release = c.getString(TAG_RELEASE);

                        HashMap<String, String> movie  =new HashMap<>();
                        movie.put(TAG_ID, id);
                        movie.put(TAG_TITLE, title);
                        movie.put(TAG_DURATION, duration);
                        movie.put(TAG_RELEASE, release);

                        movieList.add(movie);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
                Log.e("loadJSONObject", "obiectul este null");
        }
    }
}