package com.inostudio.weather_forecast.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Иван on 31.01.2018.
 */
@Dao
public interface WeatherDao {

    @Query("SELECT * FROM typeweather")
    List<TypeWeather> getAllTypeWeathers();

    @Insert
    void insertAll(TypeWeather... weathers);
}
