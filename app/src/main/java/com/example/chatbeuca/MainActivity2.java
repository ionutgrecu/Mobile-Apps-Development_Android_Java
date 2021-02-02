package com.example.chatbeuca;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatbeuca.database.DatabaseManager3;
import com.example.chatbeuca.database.model.Movie;
import com.example.chatbeuca.util.MovieAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;

    public static final int REQUEST_CODE = 200;

    public static final int REQUEST_CODE_EDIT = 300;

    public static final String EDIT_MOVIE = "editMovie";

    public int poz;

    private ListView listView;
    List<Movie> movieList = new ArrayList<Movie>();

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), AddMovieActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        listView = findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                poz = position;
                intent = new Intent(getApplicationContext(), AddMovieActivity.class);
                intent.putExtra(EDIT_MOVIE, movieList.get(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                final Movie movie = movieList.get(position);


                final MovieAdapter adapter = (MovieAdapter) listView.getAdapter();




                /*final Dialog dlg = new Dialog(MainActivity2.this);
                LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setBackgroundColor(Color.WHITE);
                TextView mesaj = new TextView(getApplicationContext());
                mesaj.setText("Sigur doriti stergerea?");
                Button btnYes = new Button(getApplicationContext());
                btnYes.setText("Yes");
                Button btnNo = new Button(getApplicationContext());
                btnNo.setText("No");
                linearLayout.addView(mesaj);
                linearLayout.addView(btnYes);
                linearLayout.addView(btnNo);
                dlg.setContentView(linearLayout);
                dlg.show();

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        movieList.remove(movie);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "S-a sters filmul: "+movie.toString(),
                                Toast.LENGTH_LONG).show();
                        dlg.dismiss();
                    }
                });

                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Nu s-a sters nimic!",
                                Toast.LENGTH_LONG).show();
                        dlg.dismiss();
                    }
                });
*/

                AlertDialog dialog = new AlertDialog.Builder(MainActivity2.this)
                        .setTitle("Confirmare stergere")
                        .setMessage("Sigur doriti stergerea?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "Nu s-a sters nimic!",
                                        Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                movieList.remove(movie);


                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "S-a sters filmul: "+movie.toString(),
                                        Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        }).create();

                        dialog.show();

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.meniu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.opt1:
                Intent intent = new Intent(this, BNRActivity.class);
                startActivity(intent);
                break;
            case R.id.opt2:
                //Toast.makeText(this, "Ati selectat optiune 2", Toast.LENGTH_LONG).show();
                ArrayList<Movie> listMoviesNetflix = new ArrayList<>();
                ArrayList<Movie> listMoviesHBOGO = new ArrayList<>();
                for(Movie movie: movieList)
                    if(movie.getPlatforma().toUpperCase().equals("HBOGO"))
                        listMoviesHBOGO.add(movie);
                    else
                        listMoviesNetflix.add(movie);

                Intent intent2 = new Intent(this, GraficActivity.class);
                intent2.putExtra("listMoviesHBOGO", listMoviesHBOGO);
                intent2.putExtra("listMoviesNetflix", listMoviesNetflix);
                startActivity(intent2);
                break;

            case R.id.opt3:
                //Toast.makeText(this, "Ati selectat optiune 3", Toast.LENGTH_LONG).show();

                /*ExtractMovies extractMovies = new ExtractMovies(){

                    @Override
                    protected void onPostExecute(InputStream inputStream) {

                        movieList.addAll(ExtractMovies.listaFilme);

                        MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.elemlistview,
                                movieList, getLayoutInflater()){
                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);

                                Movie movie =  movieList.get(position);

                                TextView tvProfit = view.findViewById(R.id.profit);
                                if(movie.getProfit() > 100000)
                                    tvProfit.setTextColor(Color.GREEN);
                                else
                                    tvProfit.setTextColor(Color.RED);

                                return view;
                            }
                        };
                        listView.setAdapter(adapter);
                    }
                };
                try {
                    extractMovies.execute(new URL("https://pastebin.com/raw/crjKE827"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
*/
                final DatabaseManager3 movieDB = DatabaseManager3.getInstance(getApplicationContext());

                ExtractMoviesJSON extractMoviesJSON = new ExtractMoviesJSON(){

                    @Override
                    protected void onPostExecute(String s) {
                        movieList.addAll(ExtractMoviesJSON.listaFilme);

                        /*for(Movie m: movieList)
                            m.setIdCinema(100);*/

                        //movieDB.getMovieDao().insert(movieList);

                        List<Movie> listaFilmeBD = movieDB.getMoviesDao().getMoviesFromCinema(100);
                        for(Movie movie : listaFilmeBD)
                            Toast.makeText(getApplicationContext(), movie.toString(), Toast.LENGTH_LONG).show();

                        MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.elemlistview,
                                movieList, getLayoutInflater()){
                            @NonNull
                            @Override
                            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);

                                Movie movie =  movieList.get(position);

                                TextView tvProfit = view.findViewById(R.id.profit);
                                if(movie.getProfit() > 100000)
                                    tvProfit.setTextColor(Color.GREEN);
                                else
                                    tvProfit.setTextColor(Color.RED);

                                return view;
                            }
                        };
                        listView.setAdapter(adapter);
                    }
                };
                try {
                    extractMoviesJSON.execute(new URL("https://pastebin.com/raw/TG263q6t"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                return  true;
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();



                MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.elemlistview,
                        movieList, getLayoutInflater()){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);

                        Movie movie =  movieList.get(position);

                        TextView tvProfit = view.findViewById(R.id.profit);
                        if(movie.getProfit() > 100000)
                            tvProfit.setTextColor(Color.GREEN);
                        else
                            tvProfit.setTextColor(Color.RED);

                        return view;
                    }
                };
                listView.setAdapter(adapter);
            }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Movie movie = (Movie) data.getSerializableExtra(AddMovieActivity.ADD_MOVIE);

            if (movie != null) {
                //Toast.makeText(getApplicationContext(), movie.toString(), Toast.LENGTH_LONG).show();
                movieList.add(movie);

               /* ArrayAdapter<Movie> adapter = new ArrayAdapter<Movie>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, movieList);*/

               MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.elemlistview,
                       movieList, getLayoutInflater()){
                   @NonNull
                   @Override
                   public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                       View view = super.getView(position, convertView, parent);

                       Movie movie =  movieList.get(position);

                       TextView tvProfit = view.findViewById(R.id.profit);
                       if(movie.getProfit() > 100000)
                           tvProfit.setTextColor(Color.GREEN);
                       else
                           tvProfit.setTextColor(Color.RED);

                       return view;
                   }
               };

                listView.setAdapter(adapter);
            }
        }
        else
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK && data != null) {
            Movie movie = (Movie) data.getSerializableExtra(AddMovieActivity.ADD_MOVIE);
            {
                if (movie!=null)
                {
                    movieList.get(poz).setTitle(movie.getTitle());
                    movieList.get(poz).setData(movie.getData());
                    movieList.get(poz).setRegizor(movie.getRegizor());
                    movieList.get(poz).setProfit(movie.getProfit());
                    movieList.get(poz).setGenFilm(movie.getGenFilm());
                    movieList.get(poz).setPlatforma(movie.getPlatforma());

                    MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.elemlistview,
                            movieList, getLayoutInflater()){
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);

                            Movie movie =  movieList.get(position);

                            TextView tvProfit = view.findViewById(R.id.profit);
                            if(movie.getProfit() > 100000)
                                tvProfit.setTextColor(Color.GREEN);
                            else
                                tvProfit.setTextColor(Color.RED);

                            return view;
                        }
                    };
                    listView.setAdapter(adapter);
                }
            }
        }
    }
}