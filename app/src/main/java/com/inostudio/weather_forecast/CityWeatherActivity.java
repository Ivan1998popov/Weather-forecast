package com.inostudio.weather_forecast;

import android.arch.persistence.room.Room;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inostudio.weather_forecast.database.AppDatabase;
import com.inostudio.weather_forecast.database.City;
import com.inostudio.weather_forecast.database.ImageCity;
import com.inostudio.weather_forecast.database.Temperature;
import com.inostudio.weather_forecast.database.TypeWeather;

import java.util.List;

public class CityWeatherActivity extends AppCompatActivity {

    public static final String WEATHER = "weather";

    private TextView mCityName ,mTemperature,mTypeWeather,mDescriptionWeather;
    private ImageView mCityImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        mCityName=(TextView)findViewById(R.id.city_name);
        mTemperature=(TextView)findViewById(R.id.temperature);
        mDescriptionWeather=(TextView)findViewById(R.id.description_weather);
        mTypeWeather=(TextView)findViewById(R.id.type_weather);
        mCityImage=(ImageView)findViewById(R.id.image_city);


        AppDatabase db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"weather-forecast")
                .allowMainThreadQueries().build();

        String name_city= getIntent().getStringExtra(WEATHER);
        City city= db.mCityDao().getCity(name_city);
        Temperature temperature = db.mTemperatureDao().getWeatherById(city.getId());
        TypeWeather typeWeather=db.mWeatherDao().getWeatherById(city.getId());
        ImageCity imageCity=db.mImageDao().getImageById(city.getId());
        int resID = getResources().getIdentifier(imageCity.getPathPicture() , "drawable", getPackageName());
        mCityImage.setImageResource(resID);
        mCityName.setText(city.getCityName());
        mTemperature.setText(temperature.getTemperature()+" °C");
        mTypeWeather.setText(typeWeather.getWeatherName()+" : ");
        mDescriptionWeather.setText(typeWeather.getDescriptionWeather());

    }
}
