package com.inostudio.weather_forecast.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Иван on 30.01.2018.
 */
@Dao

public interface CityDao {
    @Query("SELECT * FROM city")
    List<City> getAllCities();

    @Insert
    void insertAll(City  cities);
}
