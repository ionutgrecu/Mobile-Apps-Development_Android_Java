package com.example.chatbeuca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.chatbeuca.database.model.CursValutar;
import com.example.chatbeuca.util.Network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class BNRActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int OPTIUNE1 = 0;
    public static final int OPTIUNE2 = 1;
    public static final int OPTIUNE3 = 2;
    public static final int MAPS = 3;


    private TextView tv, tv2;

    private EditText EUR, USD, GBP, XAU;

    @Override
    public void onClick(View view) {
       /* EUR.setText("4.87");
        USD.setText("4.33");
        GBP.setText("5.22");
        XAU.setText("230.7");
        Toast.makeText(this, R.string.toast1, Toast.LENGTH_LONG).show();*/

       Network n = new Network(){
           @Override
           protected void onPostExecute(InputStream inputStream) {

               //Toast.makeText(getApplicationContext(), Network.sbuf, Toast.LENGTH_LONG).show();

               tv.setText(cv.getData());
               EUR.setText(cv.getEuro());
               USD.setText(cv.getDolar());
               GBP.setText(cv.getGbp());
               XAU.setText(cv.getAur());

           }
       };
        try {
            n.execute(new URL("https://www.bnr.ro/nbrfxrates.xml"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    public enum Location {
        // enumerate symbols
        INTERNAL, DATABASE, SDCARD
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("lifecycle", "Apel metoda onCreate()");

        tv = findViewById(R.id.tvHello);
        //tv2 = findViewById(R.id.tvHello2);

        ///tv.setText("Noul text setat");
        //tv2.setText("Noul text setat pe al doilea TextView");

        EUR = findViewById(R.id.editTextEUR);
        USD = findViewById(R.id.editTextUSD);
        GBP = findViewById(R.id.editTextGBP);
        XAU = findViewById(R.id.editTextXAU);

        Spinner sp1 = findViewById(R.id.spinner1);

        //String[] values = {"INTERNAL", "DATABASE", "SDCARD"};

        String[] values = new String[Location.values().length];
        int i = 0;
        for (Location loc : Location.values())
            values[i++] = loc.toString();

        ArrayAdapter<String> adaptor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,
                values);

        sp1.setAdapter(adaptor);

        Button btn1 = findViewById(R.id.btnAfisare);
        btn1.setOnClickListener(this);


        Button btn2 = findViewById(R.id.btnSalvare);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), R.string.toast2, Toast.LENGTH_LONG).show();
                    CursValutar cv = new CursValutar(tv.getText().toString(), EUR.getText().toString(),
                            USD.getText().toString(), GBP.getText().toString(), XAU.getText().toString());



                   // DatabaseManager2 cursuriDB = DatabaseManager2.getInstanta(getApplicationContext());

                   // cursuriDB.getCursDao().insert(cv);

                /*List<CursValutar> listaRoom = cursuriDB.getCursDao().getAll();
                if(listaRoom!=null)
                    for(CursValutar cursValutar:listaRoom)
                        Toast.makeText(getApplicationContext(), cursValutar.toString(), Toast.LENGTH_LONG).show();*/

                try {
                    scrieInFiser("fisier.dat", cv);
                    cv = null;
                    cv = citireDinFisier("fisier.dat");
                    Toast.makeText(getApplicationContext(), cv.toString(), Toast.LENGTH_LONG).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }



    private CursValutar citireDinFisier(String numeFisier)
        throws IOException
    {
        FileInputStream fisier = openFileInput(numeFisier);
        DataInputStream dis = new DataInputStream(fisier);
        String data = dis.readUTF();
        String euro = dis.readUTF();
        String dolar = dis.readUTF();
        String gbp = dis.readUTF();
        String aur = dis.readUTF();
        CursValutar cv = new CursValutar(data, euro, dolar, gbp, aur);
        fisier.close();
        return cv;
    }
    private void scrieInFiser(String numeFisier, CursValutar cv)
            throws IOException
    {
        FileOutputStream fisier = openFileOutput(numeFisier, Activity.MODE_PRIVATE);
        DataOutputStream dos = new DataOutputStream(fisier);
        dos.writeUTF(cv.getData());
        dos.writeUTF(cv.getEuro());
        dos.writeUTF(cv.getDolar());
        dos.writeUTF(cv.getGbp());
        dos.writeUTF(cv.getAur());
        dos.flush();
        fisier.close();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("lifecycle", "Apel metoda onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("lifecycle", "Apel metoda onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("lifecycle", "Apel metoda onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("lifecycle", "Apel metoda onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("lifecycle", "Apel metoda onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("lifecycle", "Apel metoda onDestroy()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, OPTIUNE1, 0, "Setari");
        menu.add(0, OPTIUNE2, 1, "Vizualizare Firebase");
        menu.add(0, OPTIUNE3, 2, "JSON");
        menu.add(0, MAPS, 3, "Google Maps");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case OPTIUNE1:
                //Toast.makeText(this, "Ati ales prima optiune", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent1);
                break;
            case OPTIUNE2:
                //Toast.makeText(this, "Ati ales a doua optiune", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent2);
                break;
            case MAPS:
                //Toast.makeText(this, "Ati ales a treia optiune", Toast.LENGTH_LONG).show();
                Intent intent3 = new Intent(getApplicationContext(), ChatActivity.class);
                startActivity(intent3);
                break;
            case OPTIUNE3:
                //Toast.makeText(this, "Ati ales a treia optiune", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), JSONActivity.class);
                startActivity(intent);

                return true;
        }
        return false;
    }
}