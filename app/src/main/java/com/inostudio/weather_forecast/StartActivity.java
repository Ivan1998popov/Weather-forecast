package com.inostudio.weather_forecast;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.inostudio.weather_forecast.database.AppDatabase;
import com.inostudio.weather_forecast.database.City;
import com.inostudio.weather_forecast.database.ImageCity;
import com.inostudio.weather_forecast.database.Temperature;
import com.inostudio.weather_forecast.database.TypeWeather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static android.provider.Telephony.Mms.Part.FILENAME;
import static java.io.FileDescriptor.in;

public class StartActivity extends AppCompatActivity {


    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    List<City> cities;
    List<Temperature> temperatures;
    List<TypeWeather> weather_types;
    List<ImageCity> image_list;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_city:
                    mAdapter = new CityAdapter(cities);
                    mRecyclerView.setAdapter(mAdapter);
                    return true;
                case R.id.navigation_settings:
                    startActivity(new Intent(StartActivity.this,SettingsActivity.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView =(RecyclerView)findViewById(R.id.cities_list);

        AppDatabase db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"weather-forecast")
                .allowMainThreadQueries().build();

        cities = db.mCityDao().getAllCities();
        temperatures = db.mTemperatureDao().getAllTemperature();
        weather_types = db.mWeatherDao().getAllTypeWeathers();
        image_list=db.mImageDao().getAllImageCity();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new CityAdapter(swelling_database(cities,db));
        mRecyclerView.setAdapter(mAdapter);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void doPrint() {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);

        String jobName = "Jobname";

        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
        builder.setColorMode(PrintAttributes.COLOR_MODE_MONOCHROME);
        PrintAttributes attr = builder.build();

        //printManager.print(jobName, new PrintDocumentAdapterForFiles(ctx, new File(filePath), jobName), attr );
    }

    public List<City> swelling_database(List<City> cities,AppDatabase db){
        if (cities.size() == 0) {
            String[] list_cities=getResources().getStringArray(R.array.cities);
            String[] list_temperatures=getResources().getStringArray(R.array.temperature);
            String[] list_weather_types=getResources().getStringArray(R.array.type_weather);
            String[] list_weather_descr=getResources().getStringArray(R.array.descr_weather);
            String[] cityImage_path=getResources().getStringArray(R.array.city_path_image);
            for (String list_city : list_cities) {
                City city = new City(list_city);
                cities.add(city);
                db.mCityDao().insertAll(city);
            }
            for (String list_temperature : list_temperatures) {
                Temperature temperature = new Temperature(list_temperature);
                temperatures.add(temperature);
                db.mTemperatureDao().insertAll(temperature);
            }
            for (int i = 0; i < list_weather_types.length; i++) {
                TypeWeather weather_type = new TypeWeather(list_weather_types[i], list_weather_descr[i]);
                weather_types.add(weather_type);
                db.mWeatherDao().insertAll(weather_type);
            }
            for (int i = 0; i <cityImage_path.length ; i++) {
                ImageCity imageCity=new ImageCity(cityImage_path[i]);
                image_list.add(imageCity);
                db.mImageDao().insertAll(imageCity);
            }

            Log.d("LOG","Loaded from file");
        } else {
            Log.d("LOG","Loaded from db");
        }
        return cities;
    }

    /*
    ResizableImageView example in XML

    <view
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            class="com.inostudio.weather_forecast.ResizableImageView"
            android:id="@+id/photo"
            android:layout_gravity="center"
            android:background="@drawable/jobs_portrait"
            android:clickable="true"
       		android:onClick="update" />

    * */

}