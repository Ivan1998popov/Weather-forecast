package com.inostudio.weather_forecast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView mSettingsText = findViewById(R.id.settings_text);
        mSettingsText.setText("Здесь ничего нет");
    }
}
