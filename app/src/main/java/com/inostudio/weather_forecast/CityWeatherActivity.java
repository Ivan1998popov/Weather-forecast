package com.inostudio.weather_forecast;

import android.arch.persistence.room.Room;
import android.content.Intent;
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

    private TextView mCityName ,mTemperature,mTypeWeather,mDescriptionWeather, mWindSpeed, mPressure, mHumidity;
    private ImageView mCityImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        mCityName=(TextView)findViewById(R.id.city_name);
        mTemperature=(TextView)findViewById(R.id.temperature);
        mWindSpeed=(TextView)findViewById(R.id.wind_speed);
        mPressure=(TextView)findViewById(R.id.pressure);
        mHumidity=(TextView)findViewById(R.id.humidity);
        mDescriptionWeather=(TextView)findViewById(R.id.description_weather);
        mTypeWeather=(TextView)findViewById(R.id.type_weather);
        mCityImage=(ImageView)findViewById(R.id.image_city);


        AppDatabase db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"weather-forecast")
                .allowMainThreadQueries().build();

        String name_city= getIntent().getStringExtra(WEATHER);
        final City city= db.mCityDao().getCity(name_city);
        Temperature temperature = db.mTemperatureDao().getWeatherById(city.getId());
        TypeWeather typeWeather=db.mWeatherDao().getWeatherById(city.getId());
        ImageCity imageCity=db.mImageDao().getImageById(city.getId());
        int resID = getResources().getIdentifier(imageCity.getPathPicture() , "drawable", getPackageName());
        mCityImage.setImageResource(resID);
        mCityName.setText(city.getCityName());
        mTemperature.setText(temperature.getTemperature()+" °C");
        mWindSpeed.setText(temperature.getWindSpeed()+" м/с");
        mPressure.setText(temperature.getPressure()+" мм рт. ст.");
        mHumidity.setText(temperature.getHumidity()+" %");
        mTypeWeather.setText(typeWeather.getWeatherName()+" : ");
        mDescriptionWeather.setText(typeWeather.getDescriptionWeather());

        mCityImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),CityDescrActivity.class);
                intent.putExtra(CityDescrActivity.DESCRIPTION,city.getCityDescr());
                view.getContext().startActivity(intent);
            }
        });
    }
}
