package com.inostudio.weather_forecast;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inostudio.weather_forecast.database.AppDatabase;
import com.inostudio.weather_forecast.database.City;
import com.inostudio.weather_forecast.database.ImageCity;
import com.inostudio.weather_forecast.database.Temperature;
import com.inostudio.weather_forecast.database.TypeWeather;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class CityWeatherActivity extends AppCompatActivity {

    public static final String WEATHER = "weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        TextView mCityName = findViewById(R.id.city_name);
        TextView mTemperature = findViewById(R.id.temperature);
        TextView mWindSpeed = findViewById(R.id.wind_speed);
        TextView mPressure = findViewById(R.id.pressure);
        TextView mHumidity = findViewById(R.id.humidity);
        TextView mDescriptionWeather = findViewById(R.id.description_weather);
        TextView mTypeWeather = findViewById(R.id.type_weather);
        ImageView mCityImage = findViewById(R.id.image_city);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "weather-forecast")
                .allowMainThreadQueries().build();

        String name_city = getIntent().getStringExtra(WEATHER);
        final City city = db.mCityDao().getCity(name_city);
        Temperature temperature = db.mTemperatureDao().getWeatherById(city.getId());
        TypeWeather typeWeather = db.mWeatherDao().getWeatherById(city.getId());
        ImageCity imageCity = db.mImageDao().getImageById(city.getId());
        int resID = getResources().getIdentifier(imageCity.getPathPicture(), "drawable", getPackageName());
        mCityImage.setImageResource(resID);
        mCityName.setText(city.getCityName());
        mTemperature.setText(String.format("%s °C", temperature.getTemperature()));
        mWindSpeed.setText(String.format("%s м/с", temperature.getWindSpeed()));
        mPressure.setText(String.format("%s мм рт. ст.", temperature.getPressure()));
        mHumidity.setText(String.format("%s %%", temperature.getHumidity()));
        mTypeWeather.setText(String.format("%s : ", typeWeather.getWeatherName()));
        mDescriptionWeather.setText(typeWeather.getDescriptionWeather());

        //обработчик нажатий на изображение
        mCityImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CityDescrActivity.class);
                intent.putExtra(CityDescrActivity.DESCRIPTION, city.getCityDescr());
                view.getContext().startActivity(intent);
            }
        });
    }

    //метод сохранения экрана в файл
    public void takeScreenshot(View view) {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            String folderPath = Environment.getExternalStorageDirectory().toString() + "/Pictures/WeatherForecast/";
            String imagePath = Environment.getExternalStorageDirectory().toString() + "/Pictures/WeatherForecast/" + now + ".jpg";

            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            //если есть разрешение на запись - сохраняем
            if (hasPermissions()) {
                //создаем папку, если ее нет
                File folder = new File(folderPath);
                if (!folder.exists()) {
                    boolean mkdirs = folder.mkdirs();
                }

                //сохраняем изображение в папку
                File imageFile = new File(imagePath);
                FileOutputStream outputStream = new FileOutputStream(imageFile);
                int quality = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                outputStream.flush();
                outputStream.close();
                Toast.makeText(this, R.string.file_saved, Toast.LENGTH_SHORT).show();
            } else {
                //если нет разрешения на запись - запрашиваем
                requestPerms();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    //проверка разрешения на запись
    private boolean hasPermissions(){
        String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        return checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    //запрос разрешения на запись
    private void requestPerms(){
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permissions,123);
        }
    }
}
