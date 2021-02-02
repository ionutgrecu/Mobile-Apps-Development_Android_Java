package com.example.chatbeuca;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_settings);

        addPreferencesFromResource(R.layout.activity_settings);

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        boolean areAer = prefs.getBoolean("cbAer", false);
        String denumireCinema = prefs.getString("etCinema", null);
    }
}