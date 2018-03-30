package com.example.user.games4days;

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