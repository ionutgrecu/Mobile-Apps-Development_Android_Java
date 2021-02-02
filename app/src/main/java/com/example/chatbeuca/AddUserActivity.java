package com.example.chatbeuca;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatbeuca.database.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

import com.example.chatbeuca.util.DateConverter;

public class AddUserActivity extends AppCompatActivity {

    public static final String USER_KEY = "userKey";
    private TextInputEditText tietDate;
    private TextInputEditText tietTime;
    private Spinner spnCategory;
    private TextInputEditText tietDescription;
    private ImageButton ibSave;

    private Intent intent;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        initComponents();
        intent = getIntent();
        if (intent.hasExtra(USER_KEY)) {
            user = (User) intent.getSerializableExtra(USER_KEY);
            updateViewsFromExpense(user);
        }
    }

    private void updateViewsFromExpense(User user) {
        if (user == null) {
            return;
        }
        if (user.getDate() != null) {
            tietDate.setText(DateConverter.fromDate(user.getDate()));
        }
        if (user.getTime() != null) {
            tietTime.setText(String.valueOf(user.getTime()));
        }
        tietDescription.setText(user.getDescription());
        selectCategory(user);
    }

    private void selectCategory(User user) {
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spnCategory.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            String item = (String) adapter.getItem(i);
            if (item != null && item.equals(user.getCategory())) {
                spnCategory.setSelection(i);
                break;
            }
        }
    }

    private void initComponents() {
        tietDate = findViewById(R.id.tiet_add_user_date);
        tietTime = findViewById(R.id.tiet_add_user_time);
        spnCategory = findViewById(R.id.spn_add_user_category);
        tietDescription = findViewById(R.id.tiet_add_user_description);
        ibSave = findViewById(R.id.ibtn_add_user_save);
        addCategoryAdapter();
        ibSave.setOnClickListener(saveExpenseEventListener());
    }

    private View.OnClickListener saveExpenseEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    createFromViews();
                    intent.putExtra(USER_KEY, user);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }

    private void createFromViews() {
        Date date = DateConverter.fromString(tietDate.getText().toString());
        String category = spnCategory.getSelectedItem().toString();
        Double time = Double.parseDouble(tietTime.getText().toString());
        String description = tietDescription.getText().toString();
        if (user == null) {
            user = new User(date, category, time, description);
        } else {
            user.setDate(date);
            user.setCategory(category);
            user.setTime(time);
            user.setDescription(description);
        }
    }

    private void addCategoryAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.add_expense_category_values,
                android.R.layout.simple_spinner_dropdown_item);
        spnCategory.setAdapter(adapter);
    }

    private boolean validate() {
        if (tietDate.getText() == null || tietDate.getText().toString().trim().isEmpty()
                || DateConverter.fromString(tietDate.getText().toString()) == null) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_user_date_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        if (tietTime.getText() == null || tietTime.getText().toString().isEmpty()
                || Double.parseDouble(tietTime.getText().toString()) < 0) {
            Toast.makeText(getApplicationContext(),
                    R.string.add_user_time_error_message,
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }
}