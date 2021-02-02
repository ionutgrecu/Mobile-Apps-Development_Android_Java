package com.example.chatbeuca;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SetariActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_setari);//deoarece extinde PreferenceActivity, inlocuim cu:
        addPreferencesFromResource(R.layout.activity_setari);

        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);

        boolean sex=prefs.getBoolean("cbSex",false);
        String cititor=prefs.getString("etCititor",null);
        String usernamecititor=prefs.getString("usernameCititor",null);




    }
}