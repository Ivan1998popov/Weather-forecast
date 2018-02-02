package com.inostudio.weather_forecast.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Иван on 30.01.2018.
 */
@Dao

public interface CityDao {
    @Query("SELECT * FROM City")
    List<City> getAllCities();

    @Query("SELECT *FROM City  WHERE city_name= :city")
    City getCity(String city);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(City cities);
}
