package com.example.chatbeuca;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.chatbeuca.util.ChMode;
import com.example.chatbeuca.util.DateConverter;
import com.example.chatbeuca.util.Participant;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;


import static android.widget.ArrayAdapter.createFromResource;

public class AddActivity extends AppCompatActivity {
    public static final String PARTICIPANT_KEY = "participant_key";
    private final DateConverter dateConverter = new DateConverter();

    //declarare variabile java corespunzatoare widget-urilor din add_activity.xml
    private TextInputEditText tietName;
    private TextInputEditText tietRegCode;
    private TextInputEditText tietRegDate;
    private RadioGroup rgChMode;
    private Spinner spnGroups;
    private Button btnSave;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        //apel catre metoda care realizarea legatura dintre variabilele Java
        // si componentele vizuale din add_activity.xml
        initComponents();
        //apel metoda pentru popularea spinner-ului cu elemente
        populateSpnFaculties();
        //atasare eveniment de click pe butonul salveaza
        btnSave.setOnClickListener(addSaveClickEvent());
        //preluarea intentului cu care s-a deschis activitatea
        intent = getIntent();
    }

    private void initComponents() {
        //legare textInputEditText nume
        tietName = findViewById(R.id.tiet_add_name);
        //legare textInputEditText varsta
        //.
        //.
        //.
        tietRegCode = findViewById(R.id.tiet_add_regCode);
        tietRegDate = findViewById(R.id.tiet_add_reg_date);
        rgChMode = findViewById(R.id.rg_add_chat_mode);
        spnGroups = findViewById(R.id.spn_add_teams);
        //legare buton salveaza
        btnSave = findViewById(R.id.btn_add_save);
    }

    private void populateSpnFaculties() {
        //creare adapter care asigura convertirea unei colectii de string-uri
        // la o colectie de view (TextView)
        ArrayAdapter<CharSequence> adapter = createFromResource(getApplicationContext(),
                R.array.add_team,
                android.R.layout.simple_spinner_dropdown_item);
        //atasam adapter catre spinner
        spnGroups.setAdapter(adapter);
    }

    private View.OnClickListener addSaveClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validarea campurilor de intrare
                if (validate()) {
                    //construire obiect java cu informatiile din interfata
                    Participant participant = buildParticipantFromWidgets();
                    //punere in intent a studentului pe care dorim sa-l trimitem catre MainActivity
                    intent.putExtra(PARTICIPANT_KEY, participant);
                    //trimiterea intent-ului catre MainActivity
                    setResult(RESULT_OK, intent);
                    //inchidere activitate curenta
                    finish();
                }
            }
        };
    }

    private boolean validate() {
        //validare pentru campul name
        if (tietName.getText() == null || tietName.getText().toString().trim().length() < 3) {
            //Toasts - can't interact with
            Toast.makeText(getApplicationContext(),
                    R.string.add_invalid_name_error,
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        //validare pentru campul regCode
        if (tietRegCode.getText() == null || tietRegCode.getText().toString().trim().length() == 0
                || Integer.parseInt(tietRegCode.getText().toString().trim()) < 1
                || Integer.parseInt(tietRegCode.getText().toString().trim()) > 100) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_invalid_regCode,
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        //validare pentru campul reg date
        if (tietRegDate.getText() == null
                || DateConverter.fromString(tietRegDate.getText().toString().trim()) == null) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_invalid_regDate,
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        return true;
    }

    private Participant buildParticipantFromWidgets() {
        //extragere nume
        String name = tietName.getText().toString();
        //extragere regCode
        int regCode = Integer.parseInt(tietRegCode.getText().toString().trim());
        //extragere ziua inreg
        Date regDate = DateConverter.fromString(tietRegDate.getText().toString().trim());
        //identificare forma de chatting in functie de radio buttonul selectat in interfata
        //selectam valoarea default sa fie full Encrypted
        ChMode chMode = ChMode.Encrypted;
        //altfel alegem NORMAL
        if (rgChMode.getCheckedRadioButtonId() == R.id.rb_add_ch_mode_normal) {
            chMode = ChMode.NORMAL;
        }
        //preluam ce este selectat in spinner
        String faculty = spnGroups.getSelectedItem().toString();
        return new Participant(name, regCode, regDate, chMode, faculty);
    }
}