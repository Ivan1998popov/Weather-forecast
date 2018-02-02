package com.inostudio.weather_forecast;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inostudio.weather_forecast.database.City;

import java.util.List;

/**
 * Created by Иван on 30.01.2018.
 */

class CityAdapter extends android.support.v7.widget.RecyclerView.Adapter<CityAdapter.ViewHolder> {

    List<City> cities;

    public CityAdapter(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityAdapter.ViewHolder holder, final int position) {
        holder.cityName.setText(cities.get(position).getCityName());
        //обработчик нажатий элемента в списке
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CityWeatherActivity.class);
                intent.putExtra(CityWeatherActivity.WEATHER, cities.get(position).getCityName());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView cityName;

        public ViewHolder(View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.item_city);
        }
    }
}
