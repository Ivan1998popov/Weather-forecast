package com.inostudio.weather_forecast.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Иван on 30.01.2018.
 */
@Entity(tableName = "City")
public class City {
    public City(String cityName, String cityDescr) {

        mCityName = cityName;
        mCityDescr = cityDescr;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "city_name")
    private String mCityName;
    @ColumnInfo(name = "city_descr")
    private String mCityDescr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public String getCityDescr() {
        return mCityDescr;
    }

    public void setCityDescr(String CityDescr) {
        mCityDescr = CityDescr;
    }
}
