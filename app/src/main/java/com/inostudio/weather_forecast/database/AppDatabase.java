package com.inostudio.weather_forecast.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {City.class, Temperature.class, TypeWeather.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CityDao mCityDao();
    public abstract WeatherDao mWeatherDao();
    public abstract TemperatureDao mTemperatureDao();

}
