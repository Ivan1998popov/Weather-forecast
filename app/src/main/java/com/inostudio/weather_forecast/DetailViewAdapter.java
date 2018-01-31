package com.inostudio.weather_forecast;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inostudio.weather_forecast.database.City;
import com.inostudio.weather_forecast.database.Temperature;
import com.inostudio.weather_forecast.database.TypeWeather;

import java.util.List;

/**
 * Created by Иван on 30.01.2018.
 */

class DetailViewAdapter extends RecyclerView.Adapter<DetailViewAdapter.ViewHolder> {

    List<City> cities;
    List<Temperature> temperatures;
    List<TypeWeather> weather_types;

    public DetailViewAdapter(List<City> cities, List<Temperature> temperatures, List<TypeWeather> weather_types) {
        this.cities = cities;
        this.temperatures = temperatures;
        this.weather_types = weather_types;
    }

    @Override
    public DetailViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailViewAdapter.ViewHolder holder, final int position) {
        holder.cityName.setText( cities.get(position).getCityName());
        holder.temperature.setText( temperatures.get(position).getTemperature());
        holder.weatherType.setText( weather_types.get(position).getWeatherName());
        holder.weatherDescr.setText( weather_types.get(position).getDescriptionWeather());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LOG",position + " pressed");
            }
        });
    }


    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cityName;
        public TextView temperature;
        public TextView weatherType;
        public TextView weatherDescr;
        public ViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.item_city);
            temperature = itemView.findViewById(R.id.item_temperature);
            weatherType = itemView.findViewById(R.id.item_weather_type);
            weatherDescr = itemView.findViewById(R.id.item_weather_descr);
        }
    }
}
