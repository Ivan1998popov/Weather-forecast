package com.inostudio.weather_forecast.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Иван on 31.01.2018.
 */
@Dao
public interface TemperatureDao {

    @Query("SELECT * FROM temperature")
    List<Temperature> getAllTemperature();

    @Insert
    void insertAll(Temperature... temperatures);
}
