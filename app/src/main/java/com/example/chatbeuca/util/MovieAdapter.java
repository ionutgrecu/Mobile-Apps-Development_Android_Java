package com.example.chatbeuca.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chatbeuca.R;
import com.example.chatbeuca.database.model.Movie;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private final Context context;
    private final int resource;
    private final List<Movie> movieList;
    private final LayoutInflater layoutInflater;

    public MovieAdapter(@NonNull Context context, int resource, List<Movie> movieList, LayoutInflater layoutInflater) {
        super(context, resource, movieList);
        this.context = context;
        this.resource = resource;
        this.movieList = movieList;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = layoutInflater.inflate(resource, parent, false);
        Movie movie = movieList.get(position);

        if (movie!=null)
        {
            TextView tv1 = view.findViewById(R.id.titlu);
            tv1.setText(movie.getTitle());

            TextView tv2 = view.findViewById(R.id.data);
            tv2.setText(movie.getData().toString());

            TextView tv3 = view.findViewById(R.id.regizor);
            tv3.setText(movie.getRegizor());

            TextView tv4 = view.findViewById(R.id.profit);
            tv4.setText(String.valueOf(movie.getProfit()));

            TextView tv5 = view.findViewById(R.id.gen);
            tv5.setText(movie.getGenFilm());

            TextView tv6 = view.findViewById(R.id.platforma);
            tv6.setText(movie.getPlatforma());
        }

        return view;
    }
}
