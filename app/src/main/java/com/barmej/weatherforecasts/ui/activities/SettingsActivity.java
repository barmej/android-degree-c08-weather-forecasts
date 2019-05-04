package com.barmej.weatherforecasts.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.barmej.weatherforecasts.R;

/**
 * Settings activity that host Settings fragment
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

}
