package com.inostudio.weather_forecast;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.os.Build;
import android.os.Bundle;
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
import com.inostudio.weather_forecast.database.Temperature;

import java.io.BufferedReader;
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

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "D:\\Study\\Projects android\\Weather-forecast\\app\\src\\main\\res\\values-ru\\cities";
    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_city:
                    mTextMessage.setText(R.string.title_city);
                    return true;
                case R.id.navigation_settings:
                    mTextMessage.setText("Навигатор настроек");
                    return true;
            }
            return false;
        }
    };

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        mRecyclerView =(RecyclerView)findViewById(R.id.cities_list);

        AppDatabase db= Room.databaseBuilder(getApplicationContext(),AppDatabase.class,"weather-forecast")
              .allowMainThreadQueries().build();


       List<City> cities =db.mCityDao().getAllCities();



        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter =new CityAdapter(swelling_database(cities));
        mRecyclerView.setAdapter(mAdapter);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public List<City> swelling_database(List<City> cities){
        String[] list_cities=getResources().getStringArray(R.array.cities);
        for (int i = 0; i <list_cities.length ; i++) {
            City city =new City(list_cities[i]);
            cities.add(city);
            Log.d("LOG",city.getId()+" = "+city.getCityName());
        }
        return cities;
    }



}