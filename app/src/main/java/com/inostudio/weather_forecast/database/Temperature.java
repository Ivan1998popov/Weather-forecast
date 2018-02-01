package com.inostudio.weather_forecast.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "Temperature")
public class Temperature {
    public Temperature(String temperature, String windSpeed, String pressure, String humidity) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    @PrimaryKey(autoGenerate = true)
    private  int id;
    @ColumnInfo(name = "temperature")
    private  String temperature;
    @ColumnInfo(name = "wind_speed")
    private  String windSpeed;
    @ColumnInfo(name = "pressure")
    private  String pressure;
    @ColumnInfo(name = "humidity")
    private  String humidity;

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

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
