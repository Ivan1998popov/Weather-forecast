package com.inostudio.weather_forecast.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Иван on 31.01.2018.
 */
@Entity
public class TypeWeather {

   public TypeWeather(String weatherName,String descriptionWeather){
       this.mWeatherName=weatherName;
       this.mDescriptionWeather=descriptionWeather;
   }

    @PrimaryKey(autoGenerate = true)
    private  int id;
    @ColumnInfo(name = "weather_name")
    private  String mWeatherName;
    @ColumnInfo(name ="weather_description")
    private  String mDescriptionWeather;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeatherName() {
        return mWeatherName;
    }

    public void setWeatherName(String weatherName) {
        mWeatherName = weatherName;
    }

    public String getDescriptionWeather() {
        return mDescriptionWeather;
    }

    public void setDescriptionWeather(String descriptionWeather) {
        mDescriptionWeather = descriptionWeather;
    }
}
