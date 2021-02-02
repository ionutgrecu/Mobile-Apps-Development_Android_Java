package com.example.chatbeuca;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatbeuca.database.model.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BarChartActivity extends AppCompatActivity {

    ArrayList<Movie> list;
    LinearLayout layout;
    Map<String, Integer> source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        Intent intent = getIntent();

        list = (ArrayList<Movie>) intent.getSerializableExtra("list");

        source = getSource(list);

        layout = findViewById(R.id.layoutBar);
        layout.addView(new BarChartView(getApplicationContext(), source));

    }

    private Map<String, Integer> getSource(List<Movie> movies)
    {
        if(movies==null || movies.isEmpty())
            return new HashMap<>();
        else
        {
            Map<String, Integer> results = new HashMap<>();
            for(Movie movie: movies)
                if(results.containsKey(movie.getPlatforma()))
                    results.put(movie.getPlatforma(), results.get(movie.getPlatforma())+1);
                else
                    results.put(movie.getPlatforma(), 1);
                return results;
        }
    }
}