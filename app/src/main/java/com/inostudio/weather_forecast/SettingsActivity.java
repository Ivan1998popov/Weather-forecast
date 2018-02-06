package com.inostudio.weather_forecast;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.inostudio.weather_forecast.database.AppDatabase;
import com.inostudio.weather_forecast.database.Temperature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    public Button btnUpdateDatabase;
    public List<Temperature> temperaturesToAdd;
    public Context ctx = this;

    String temperatureStr, wind_speedStr, pressureStr, humidityStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "weather-forecast")
                .allowMainThreadQueries().build();

        btnUpdateDatabase = findViewById(R.id.update_database);
        btnUpdateDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temperaturesToAdd = db.mTemperatureDao().getAllTemperature();
                String[] list_cities = getResources().getStringArray(R.array.update_cities);
                for (int i = 0; i < list_cities.length; i++) {
                    new OpenWeatherMapTask(list_cities[i], i, db).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        });
    }

    public void updateDB(AppDatabase db) {
        for (int i = 0; i <temperaturesToAdd.size(); i++) {
            db.mTemperatureDao().updateTemperature(temperaturesToAdd.get(i));
        }

        Toast.makeText(ctx, R.string.db_updated, Toast.LENGTH_SHORT).show();
    }

    public String trans_kel(String kel) {
        double cel = Double.parseDouble(kel) - 273.15;
        return String.valueOf(Math.rint(cel));
    }

    public  class OpenWeatherMapTask extends AsyncTask<Void, Void, String> {

        String cityName;
        String dummyAppid = "293da20ad6da8e2bb2974cc9760fbf87";
        String queryWeather = "http://api.openweathermap.org/data/2.5/weather?q=";
        String queryDummyKey = "&appid=" + dummyAppid;
        Integer cityID;
        AppDatabase db;

        OpenWeatherMapTask(String cityName, Integer cityID, AppDatabase db) {
            this.cityName = cityName;
            this.cityID = cityID;
            this.db = db;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            String queryReturn;

            String query = null;
            try {
                query = queryWeather + URLEncoder.encode(cityName, "UTF-8") + queryDummyKey;
                queryReturn = sendQuery(query);
                result += ParseJSON(queryReturn);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            temperaturesToAdd.get(cityID).setTemperature(temperatureStr);
            temperaturesToAdd.get(cityID).setWindSpeed(wind_speedStr);
            temperaturesToAdd.get(cityID).setPressure(pressureStr);
            temperaturesToAdd.get(cityID).setHumidity(humidityStr);
            if (cityID == 44) {
                updateDB(db);
            }
        }

        private String sendQuery(String query) throws IOException {
            String result = "";

            URL searchURL = new URL(query);

            HttpURLConnection httpURLConnection = (HttpURLConnection) searchURL.openConnection();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 8192);

                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
            }

            return result;
        }

        private String ParseJSON(String json) {
            String jsonResult = "";

            try {
                JSONObject JsonObject = new JSONObject(json);
                String cod = jsonHelperGetString(JsonObject, "cod");

                if (cod != null) {
                    if (cod.equals("200")) {

                        jsonResult += jsonHelperGetString(JsonObject, "name") + "\n";
                        JSONObject sys = jsonHelperGetJSONObject(JsonObject, "sys");
                        if (sys != null) {
                            jsonResult += jsonHelperGetString(sys, "country") + "\n";
                        }
                        jsonResult += "\n";

                        JSONObject coord = jsonHelperGetJSONObject(JsonObject, "coord");
                        if (coord != null) {
                            String lon = jsonHelperGetString(coord, "lon");
                            String lat = jsonHelperGetString(coord, "lat");
                            jsonResult += "lon: " + lon + "\n";
                            jsonResult += "lat: " + lat + "\n";
                        }
                        jsonResult += "\n";

                        JSONArray weather = jsonHelperGetJSONArray(JsonObject, "weather");
                        if (weather != null) {
                            for (int i = 0; i < weather.length(); i++) {
                                JSONObject thisWeather = weather.getJSONObject(i);
                                jsonResult += "weather " + i + ":\n";
                                jsonResult += "id: " + jsonHelperGetString(thisWeather, "id") + "\n";
                                jsonResult += jsonHelperGetString(thisWeather, "main") + "\n";
                                jsonResult += jsonHelperGetString(thisWeather, "description") + "\n";
                                jsonResult += "\n";
                            }
                        }

                        JSONObject main = jsonHelperGetJSONObject(JsonObject, "main");
                        if (main != null) {
                            jsonResult += "temp: " + jsonHelperGetString(main, "temp") + "\n";   //здесь можно все данные вытащить
                            temperatureStr = trans_kel(jsonHelperGetString(main, "temp"));
                            jsonResult += "pressure: " + jsonHelperGetString(main, "pressure") + "\n";
                            pressureStr = jsonHelperGetString(main, "pressure");
                            jsonResult += "humidity: " + jsonHelperGetString(main, "humidity") + "\n";
                            humidityStr = jsonHelperGetString(main, "humidity");
                            jsonResult += "temp_min: " + jsonHelperGetString(main, "temp_min") + "\n";
                            jsonResult += "temp_max: " + jsonHelperGetString(main, "temp_max") + "\n";
                            jsonResult += "sea_level: " + jsonHelperGetString(main, "sea_level") + "\n";
                            jsonResult += "grnd_level: " + jsonHelperGetString(main, "grnd_level") + "\n";
                            jsonResult += "\n";
                        }

                        jsonResult += "visibility: " + jsonHelperGetString(JsonObject, "visibility") + "\n";
                        jsonResult += "\n";

                        JSONObject wind = jsonHelperGetJSONObject(JsonObject, "wind");
                        if (wind != null) {
                            jsonResult += "wind:\n";
                            jsonResult += "speed: " + jsonHelperGetString(wind, "speed") + "\n";
                            wind_speedStr = jsonHelperGetString(wind, "speed");
                            jsonResult += "deg: " + jsonHelperGetString(wind, "deg") + "\n";
                            jsonResult += "\n";
                        }

                    } else if (cod.equals("404")) {
                        String message = jsonHelperGetString(JsonObject, "message");
                        jsonResult += "cod 404: " + message;
                    }
                } else {
                    jsonResult += "cod == null\n";
                }

            } catch (JSONException e) {
                e.printStackTrace();
                jsonResult += e.getMessage();
            }

            return jsonResult;
        }

        private String jsonHelperGetString(JSONObject obj, String k) {
            String v = null;
            try {
                v = obj.getString(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return v;
        }

        private JSONObject jsonHelperGetJSONObject(JSONObject obj, String k) {
            JSONObject o = null;

            try {
                o = obj.getJSONObject(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return o;
        }

        private JSONArray jsonHelperGetJSONArray(JSONObject obj, String k) {
            JSONArray a = null;

            try {
                a = obj.getJSONArray(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return a;
        }
    }
}
