package com.example.chatbeuca;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.chatbeuca.database.DatabaseManager3;
import com.example.chatbeuca.database.model.Cinema;
import com.example.chatbeuca.database.model.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class AddMovieActivity extends AppCompatActivity {

    public static final String ADD_MOVIE = "adaugaFilm";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        final Spinner spinnerGen = findViewById(R.id.spinnerGenre);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.add_movie_genre, R.layout.support_simple_spinner_dropdown_item);

        spinnerGen.setAdapter(adapter);

        final EditText etTitlu = findViewById(R.id.editTextTitle);
        final EditText etData = findViewById(R.id.editTextDate);
        final EditText etRegizor = findViewById(R.id.editTextDirector);
        final EditText etProfit = findViewById(R.id.editTextProfit);

        final  String DATE_FORMAT = "MM/dd/yyyy";

        final RadioGroup radioGroup = findViewById(R.id.radioGroup);

        final Intent intent = getIntent();



       /* SharedPreferences spf=
                getSharedPreferences("MoviesSharedPrefs", 0);

        String title = spf.getString("title", null);
        String regizor = spf.getString("regizor", null);
        String data = spf.getString("data", null);
        int profit = spf.getInt("profit", 0);
        String genFilm = spf.getString("genFilm", null);
        String platorma = spf.getString("platforma", null);

        Movie m = new Movie(title, new Date(), regizor, profit, genFilm, platorma);
        Toast.makeText(getApplicationContext(), m.toString(), Toast.LENGTH_LONG).show();*/

        if(intent.hasExtra(MainActivity2.EDIT_MOVIE))
        {
            Movie movie = (Movie) intent.getSerializableExtra(MainActivity2.EDIT_MOVIE);
            etTitlu.setText(movie.getTitle());
            etData.setText(new SimpleDateFormat(DATE_FORMAT, Locale.US).format(movie.getData()));
            etRegizor.setText(movie.getRegizor());
            etProfit.setText(""+movie.getProfit());
            ArrayAdapter<String> adaptor = (ArrayAdapter<String>)spinnerGen.getAdapter();
            for(int i=0;i<adaptor.getCount();i++)
                if(adaptor.getItem(i).equals(movie.getGenFilm()))
                {
                    spinnerGen.setSelection(i);
                    break;
                }
            if(movie.getPlatforma().equals("Netflix"))
                radioGroup.check(R.id.radioButton1);
            else
                radioGroup.check(R.id.radioButton2);
        }

        Button btnSalvare = findViewById(R.id.buttonSave);
        btnSalvare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etTitlu.getText().toString().isEmpty())
                    etTitlu.setError("Introduceti titlul");
                else
                    if (etData.getText().toString().isEmpty())
                        Toast.makeText(getApplicationContext(), "Introduceti data", Toast.LENGTH_LONG).show();
                else
                    if(etRegizor.getText().toString().isEmpty())
                        etRegizor.setError("Introduceti regizor");
                    else
                    if(etProfit.getText().toString().isEmpty())
                        etProfit.setError("Introduceti suma incasari");
                    else
                    {
                        //creare obiect clasa Movie

                        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);

                        try {
                            sdf.parse(etData.getText().toString());

                            String title = etTitlu.getText().toString();
                            Date data = new Date(etData.getText().toString());
                            String regizor = etRegizor.getText().toString();
                            int profit = Integer.parseInt(etProfit.getText().toString());
                            //Gen genFilm = Gen.valueOf(spinnerGen.getSelectedItem().toString().toUpperCase());
                            String genFilm = spinnerGen.getSelectedItem().toString().toUpperCase();

                            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

                            //Platforma platforma = Platforma.valueOf(radioButton.getText().toString().toUpperCase());
                            String platforma = radioButton.getText().toString().toUpperCase();

                            DatabaseManager3 databaseManager3 = DatabaseManager3.getInstance(getApplicationContext());

                            Random random = new Random();

                            //Cinema cinema = new Cinema(100, "MoviePlex", 10, "Mall Vitan");

                            Cinema cinema = new Cinema(random.nextInt(99)+1, "MoviePlex", 10, "Mall Vitan");

                            Movie movie = new Movie(title, data, regizor, profit, genFilm, platforma);
                            movie.setIdCinema(cinema.getId());


                            /*movieDB.getCinemaDao().insert(cinema);
                            movieDB.getMovieDao().insert(movie);

                            SharedPreferences spf=
                                    getSharedPreferences("MoviesSharedPrefs",0);
                            SharedPreferences.Editor myEditor = spf.edit();

                            myEditor.putString("title", movie.getTitle());
                            myEditor.putString("data", movie.getData().toString());
                            myEditor.putString("regizor", movie.getRegizor());
                            myEditor.putInt("profit", movie.getProfit());
                            myEditor.putString("genFilm", movie.getGenFilm());
                            myEditor.putString("platforma", movie.getPlatforma());
                            myEditor.apply();*/

                            //Toast.makeText(getApplicationContext(), movie.toString(), Toast.LENGTH_LONG).show();

                            intent.putExtra(ADD_MOVIE, movie);
                            setResult(RESULT_OK, intent);
                            finish();

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }
            }
        });
    }


}