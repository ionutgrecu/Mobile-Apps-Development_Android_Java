package com.example.chatbeuca;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatbeuca.database.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import com.example.chatbeuca.asyncTask.Callback;
import com.example.chatbeuca.database.service.UserService;

public class ProfileActivity extends AppCompatActivity {

    public static final String PROFILE_SHARED = "profile_shared";
    public static final String NAME = "name";
    public static final String RB_GENDER = "rb_gender";
    private SharedPreferences sharedPreferences;

    private TextInputEditText tietName;
    private RadioGroup rgGender;
    private Button btnSave;
    private Spinner spnCategory;

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initComponents();
        userService = new UserService(getApplicationContext());
    }

    private void initComponents() {
        tietName = findViewById(R.id.tiet_profile_name);
        rgGender = findViewById(R.id.rg_profile_gender);
        btnSave = findViewById(R.id.btn_profile_save);
        spnCategory = findViewById(R.id.spn_profile_category);

        sharedPreferences = getSharedPreferences(PROFILE_SHARED, MODE_PRIVATE);

        btnSave.setOnClickListener(btnSaveEventListener());
        readDataFromSharedPreference();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(getApplicationContext(),
                        R.array.add_expense_category_values,
                        android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapter);

        spnCategory.setOnItemSelectedListener(selectCategoryEventListener());
    }

    private AdapterView.OnItemSelectedListener selectCategoryEventListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position,
                                       long id) {
                String category = (String) spnCategory.getAdapter().getItem(position);
                userService.getAllByCategory(category, getAllByCategoryCallback());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private Callback<List<User>> getAllByCategoryCallback() {
        return new Callback<List<User>>() {
            @Override
            public void runResultOnUiThread(List<User> result) {
                if (result == null || result.isEmpty()) {
                    Toast.makeText(getApplicationContext(),
                            R.string.profile_error_message_selection,
                            Toast.LENGTH_SHORT)
                            .show();
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(ProfileActivity.this)
                            .setTitle(R.string.profile_alert_title)
                            .setMessage(result.toString())
                            .create();
                    dialog.show();
                }
            }
        };
    }

    private View.OnClickListener btnSaveEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                writeDataToSharedPreference();
                finish();
            }
        };
    }

    private void readDataFromSharedPreference() {
        //citire din fisier de preferinta
        String name = sharedPreferences.getString(NAME, "");
        int rbGender = sharedPreferences.getInt(RB_GENDER, R.id.rb_profile_male);
        //actualizam interfata grafica
        if (name != null) {
            tietName.setText(name);
        }
        rgGender.check(rbGender);
    }

    private void writeDataToSharedPreference() {
        String name = tietName.getText() != null
                ? tietName.getText().toString() : null;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME, name);
        editor.putInt(RB_GENDER, rgGender.getCheckedRadioButtonId());
        editor.apply();
    }
}