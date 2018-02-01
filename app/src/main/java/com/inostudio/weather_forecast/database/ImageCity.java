package com.inostudio.weather_forecast.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Иван on 01.02.2018.
 */
@Entity(tableName = "ImageCity")
public class ImageCity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String pathPicture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPathPicture() {
        return pathPicture;
    }

    public void setPathPicture(String pathPicture) {
        this.pathPicture = pathPicture;
    }
}
