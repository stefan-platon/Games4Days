package com.example.user.laborator1;

import android.preference.PreferenceActivity;
import android.os.Bundle;

public class PrefActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName("my_prefs");
        addPreferencesFromResource(R.xml.settings);
    }
}