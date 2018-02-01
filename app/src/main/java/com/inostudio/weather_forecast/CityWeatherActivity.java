package com.inostudio.weather_forecast;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.inostudio.weather_forecast.database.AppDatabase;
import com.inostudio.weather_forecast.database.City;
import com.inostudio.weather_forecast.database.Temperature;
import com.inostudio.weather_forecast.database.TypeWeather;

import java.util.List;

public class CityWeatherActivity extends AppCompatActivity {

    public static final String WEATHER = "weather";

    private TextView mCityName ,mTemperature,mTypeWeather,mDescriptionWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        mCityName=(TextView)findViewById(R.id.city_name);
        mTemperature=(TextView)findViewById(R.id.temperature);
        mDescriptionWeather=(TextView)findViewById(R.id.description_weather);
        mTypeWeather=(TextView)findViewById(R.id.type_weather);

        AppDatabase db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"weather-forecast")
                .allowMainThreadQueries().build();

        String name_city= getIntent().getStringExtra(WEATHER);
        City city= db.mCityDao().getCity(name_city);
        Temperature temperature = db.mTemperatureDao().getWeatherById(city.getId());
        TypeWeather typeWeather=db.mWeatherDao().getWeatherById(city.getId());
        mCityName.setText(city.getCityName());
        mTemperature.setText(temperature.getTemperature()+" °C");
        mTypeWeather.setText(typeWeather.getWeatherName()+" : ");
        mDescriptionWeather.setText(typeWeather.getDescriptionWeather());

    }
}
