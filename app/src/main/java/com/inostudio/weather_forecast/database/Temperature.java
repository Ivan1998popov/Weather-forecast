package com.inostudio.weather_forecast.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity
public class Temperature {
    public Temperature(double temperature) {

        this.temperature = temperature;
    }

    @PrimaryKey(autoGenerate = true)
    private  int id;
    @ColumnInfo(name = "temperature")
    private  double temperature;

}
