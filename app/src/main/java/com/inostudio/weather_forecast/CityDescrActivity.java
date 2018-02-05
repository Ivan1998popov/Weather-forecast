package com.inostudio.weather_forecast;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.inostudio.weather_forecast.database.AppDatabase;
import com.inostudio.weather_forecast.database.City;
import com.inostudio.weather_forecast.database.ImageCity;

public class CityDescrActivity extends AppCompatActivity {

    public static final String DESCRIPTION = "description";

    private TextView mCityDescr;
    private ImageView mImageCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "weather-forecast")
                .allowMainThreadQueries().build();
        setContentView(R.layout.activity_city_descr);
        mImageCity=(ImageView)findViewById(R.id.image_city_description);
        City city= db.mCityDao().getCity(getIntent().getStringExtra(DESCRIPTION));
        ImageCity imageCity=db.mImageDao().getImageById(city.getId());
        mImageCity.setImageResource(getResources().getIdentifier(imageCity.getPathPicture(),
                "drawable", getPackageName()));
        mCityDescr = (TextView) findViewById(R.id.item_city_descr);
        mCityDescr.setText(city.getCityDescr());
    }

}
