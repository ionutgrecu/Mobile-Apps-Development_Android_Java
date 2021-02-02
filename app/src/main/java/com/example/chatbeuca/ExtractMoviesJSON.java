package com.example.chatbeuca;

import android.os.AsyncTask;
import android.util.Log;

import com.example.chatbeuca.database.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExtractMoviesJSON extends AsyncTask<URL, Void, String> {


    public static List<Movie> listaFilme = new ArrayList<>();

    JSONArray movies = null;

    @Override
    protected String doInBackground(URL... urls) {
        HttpURLConnection conn = null;

        try{
            conn = (HttpURLConnection)urls[0].openConnection();
            conn.setRequestMethod("GET");
            InputStream ist = conn.getInputStream();

            InputStreamReader isr = new InputStreamReader(ist);
            BufferedReader br = new BufferedReader(isr);
            String linie = null;
            String sbuf = "";
            while ((linie = br.readLine())!=null)
            {
                sbuf +=linie;
            }

            //parsare JSON
            loadJSON(sbuf);

            return sbuf;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void loadJSON(String jsonStr)
    {
        if(jsonStr!=null)
        {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                movies = jsonObject.getJSONArray("movies");

                for (int i = 0; i < movies.length(); i++) {
                    JSONObject c = movies.getJSONObject(i);

                    String titlu = c.getString("Titlu");
                    Date data = new Date(c.getString("Data"));
                    String regizor = c.getString("Regizor");
                    int profit = Integer.parseInt(c.getString("Profit"));
                    //Gen gen = Gen.valueOf(c.getString("GenFilm"));
                    String gen = c.getString("GenFilm");
                    //Platforma platforma = Platforma.valueOf(c.getString("Platforma"));
                    String platforma = c.getString("Platforma");

                    Movie movie = new Movie(titlu, data, regizor, profit, gen, platforma);

                    listaFilme.add(movie);

                }
            }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            Log.e("loadJSON", "JSON object is null");
        }
}


