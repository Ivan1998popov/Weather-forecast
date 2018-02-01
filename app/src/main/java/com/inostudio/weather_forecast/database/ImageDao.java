package com.inostudio.weather_forecast.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Иван on 01.02.2018.
 */
@Dao
public interface ImageDao {
    @Query("SELECT * FROM ImageCity")
    List<ImageCity> getAllImageCity();

    @Query("SELECT * FROM ImageCity WHERE id = :ID")
    ImageCity getImageById(int ID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ImageCity... imageCities);
}
