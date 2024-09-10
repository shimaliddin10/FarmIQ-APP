package com.example.farmiq.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmiq.R;
import com.example.farmiq.models.WeatherData;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private List<WeatherData> weatherDataList;

    public WeatherAdapter(List<WeatherData> weatherDataList) {
        this.weatherDataList = weatherDataList;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherData weatherData = weatherDataList.get(position);
        holder.textViewDayAndDate.setText(weatherData.getDay() + " " + weatherData.getDate());
        holder.textViewTemp.setText(weatherData.getTemperature());
        holder.textViewHumidity.setText(weatherData.getHumidity());
        holder.textViewDescription.setText(weatherData.getDescription());

        boolean isExpanded = weatherData.isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            weatherData.setExpanded(!weatherData.isExpanded());
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return weatherDataList.size();
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDayAndDate, textViewTemp, textViewHumidity, textViewDescription;
        LinearLayout expandableLayout;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDayAndDate = itemView.findViewById(R.id.textViewDayAndDate);
            textViewTemp = itemView.findViewById(R.id.textViewTemperature);
            textViewHumidity = itemView.findViewById(R.id.textViewHumidity);
            textViewDescription = itemView.findViewById(R.id.textViewWeatherDescription);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
        }
    }
}
