package com.inostudio.weather_forecast;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.inostudio.weather_forecast.database.AppDatabase;
import com.inostudio.weather_forecast.database.City;
import com.inostudio.weather_forecast.database.ImageCity;
import com.inostudio.weather_forecast.database.Temperature;
import com.inostudio.weather_forecast.database.TypeWeather;

import java.util.List;

public class StartActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    List<City> cities;
    List<Temperature> temperatures;
    List<TypeWeather> weather_types;
    List<ImageCity> image_list;

    //обработчик нажатий нижнего меню
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
                    startActivity(new Intent(StartActivity.this, SettingsActivity.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.cities_list);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "weather-forecast")
                .allowMainThreadQueries().build();

        cities = db.mCityDao().getAllCities();
        temperatures = db.mTemperatureDao().getAllTemperature();
        weather_types = db.mWeatherDao().getAllTypeWeathers();
        image_list = db.mImageDao().getAllImageCity();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new CityAdapter(swelling_database(cities, db));
        mRecyclerView.setAdapter(mAdapter);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


//    protected boolean isOnline() {
//        String cs = Context.CONNECTIVITY_SERVICE;
//        ConnectivityManager cm = (ConnectivityManager)
//                getSystemService(cs);
//        if (cm.getActiveNetworkInfo() == null) {
//            return false;
//        } else  return true;
//    }

//        public static boolean isOnline(Context context)
//    {
//        ConnectivityManager cm =
//                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        if (netInfo != null && netInfo.isConnectedOrConnecting())
//        {
//            return true;
//        }
//        return false;
//    }
    //заполнение базы данных
    public List<City> swelling_database(List<City> cities, AppDatabase db) {
        //если БД пустая, то заполняем ее данными
        if (cities.size() == 0) {
            String[] list_cities = getResources().getStringArray(R.array.cities);
            String[] list_city_descr = getResources().getStringArray(R.array.descr_city);
            String[] list_temperatures = getResources().getStringArray(R.array.temperature);
            String[] list_wind_speeds = getResources().getStringArray(R.array.windSpeed);
            String[] list_pressures = getResources().getStringArray(R.array.pressure);
            String[] list_humidities = getResources().getStringArray(R.array.humidity);
            String[] list_weather_types = getResources().getStringArray(R.array.type_weather);
            String[] list_weather_descr = getResources().getStringArray(R.array.descr_weather);
            String[] cityImage_path = getResources().getStringArray(R.array.city_path_image);
            for (int i = 0; i < list_cities.length; i++) {
                City city = new City(list_cities[i], list_city_descr[i]);
                cities.add(city);
                db.mCityDao().insertAll(city);
            }
            for (int i = 0; i < list_temperatures.length; i++) {
                Temperature temperature = new Temperature(list_temperatures[i], list_wind_speeds[i],
                        list_pressures[i], list_humidities[i]);
                temperatures.add(temperature);
                db.mTemperatureDao().insertAll(temperature);
            }
            for (int i = 0; i < list_weather_types.length; i++) {
                TypeWeather weather_type = new TypeWeather(list_weather_types[i], list_weather_descr[i]);
                weather_types.add(weather_type);
                db.mWeatherDao().insertAll(weather_type);
            }
            for (String aCityImage_path : cityImage_path) {
                ImageCity imageCity = new ImageCity(aCityImage_path);
                image_list.add(imageCity);
                db.mImageDao().insertAll(imageCity);
            }

            Log.d("LOG", "Loaded from file");
        } else {
            Log.d("LOG", "Loaded from db");
        }
        return cities;
    }
}