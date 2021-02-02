package com.example.chatbeuca;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.example.chatbeuca.database.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class GraficActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafic);

        Intent intent = getIntent();
        ArrayList<Movie> listMoviesNetflix = (ArrayList<Movie>)intent.getSerializableExtra("listMoviesNetflix");
        ArrayList<Movie> listMoviesHBOGO = (ArrayList<Movie>)intent.getSerializableExtra("listMoviesHBOGO");

        List<Double> lstProfitNetflix = new ArrayList<>();
        List<Double> lstProfitHBOGO = new ArrayList<>();

        for(Movie movie: listMoviesNetflix)
            lstProfitNetflix.add(Double.valueOf(movie.getProfit()));

        for(Movie movie: listMoviesHBOGO)
            lstProfitHBOGO.add(Double.valueOf(movie.getProfit()));

        XYPlot plot = findViewById(R.id.mySimpleXYPlot);

        plot.setTitle("Grafic profit filme");

        XYSeries series1 = new SimpleXYSeries(lstProfitHBOGO, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "HBOGO");
        plot.addSeries(series1, new LineAndPointFormatter(getApplicationContext(), R.layout.f1));

        XYSeries series2 = new SimpleXYSeries(lstProfitNetflix, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "NetFlix");
        plot.addSeries(series2, new LineAndPointFormatter(getApplicationContext(), R.layout.f2));

    }
}