package com.inostudio.weather_forecast.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Иван on 31.01.2018.
 */
@Dao
public interface WeatherDao {

    @Query("SELECT * FROM TypeWeather")
    List<TypeWeather> getAllTypeWeathers();

    @Query("SELECT * FROM TypeWeather WHERE id = :ID")
    TypeWeather getWeatherById(int ID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TypeWeather... weathers);

    @Update
    void updateAll(TypeWeather... weathers);
}
