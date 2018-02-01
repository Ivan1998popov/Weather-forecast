package com.inostudio.weather_forecast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class CityDescrActivity extends AppCompatActivity {

    public static final String DESCRIPTION = "description";

    private TextView mCityDescr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_descr);

        String descr = getIntent().getStringExtra(DESCRIPTION);

        mCityDescr = (TextView)findViewById(R.id.item_city_descr);
        mCityDescr.setText(descr);
    }

}
