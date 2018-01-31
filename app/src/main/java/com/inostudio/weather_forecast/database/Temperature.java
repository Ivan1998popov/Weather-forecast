package com.inostudio.weather_forecast.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "Temperature")
public class Temperature {
    public Temperature(String temperature) {

        this.temperature = temperature;
    }

    @PrimaryKey(autoGenerate = true)
    private  int id;
    @ColumnInfo(name = "temperature")
    private  String temperature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
