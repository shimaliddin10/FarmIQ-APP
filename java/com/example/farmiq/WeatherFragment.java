package com.example.farmiq;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmiq.adapters.WeatherAdapter;
import com.example.farmiq.models.WeatherData;

import java.util.ArrayList;
import java.util.List;

public class WeatherFragment extends Fragment {

    private RecyclerView recyclerViewWeather;
    private WeatherAdapter weatherAdapter;
    private TextView textViewMonth;
    private WebView webViewWeatherMap;
    private TextView textViewMapName;
    private TextView textViewMapLegend;
    private Spinner spinnerMapSelector;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        recyclerViewWeather = view.findViewById(R.id.recyclerViewWeather);
        textViewMonth = view.findViewById(R.id.textViewMonth);
        webViewWeatherMap = view.findViewById(R.id.webViewWeatherMap);
        textViewMapName = view.findViewById(R.id.textViewMapName);
        textViewMapLegend = view.findViewById(R.id.textViewMapLegend);
        spinnerMapSelector = view.findViewById(R.id.spinnerMapSelector);

        recyclerViewWeather.setLayoutManager(new GridLayoutManager(getContext(), 7)); // 7 columns for each day of the week

        List<WeatherData> weatherDataList = getWeatherData();
        weatherAdapter = new WeatherAdapter(weatherDataList);
        recyclerViewWeather.setAdapter(weatherAdapter);

        textViewMonth.setText("July 2024");

        setupWebView();
        setupSpinner();

        return view;
    }

    private List<WeatherData> getWeatherData() {
        List<WeatherData> weatherDataList = new ArrayList<>();
        weatherDataList.add(new WeatherData("Mon", "1", "27.0°C", "80%", "Partly cloudy"));
        weatherDataList.add(new WeatherData("Tue", "2", "27.5°C", "78%", "Partly cloudy"));
        weatherDataList.add(new WeatherData("Wed", "3", "28.5°C", "82%", "Scattered thunderstorms"));
        weatherDataList.add(new WeatherData("Thu", "4", "28.0°C", "85%", "Thunderstorms"));
        weatherDataList.add(new WeatherData("Fri", "5", "28.5°C", "88%", "Heavy rain"));
        weatherDataList.add(new WeatherData("Sat", "6", "27.0°C", "90%", "Heavy rain"));
        weatherDataList.add(new WeatherData("Sun", "7", "26.5°C", "87%", "Light rain"));
        weatherDataList.add(new WeatherData("Mon", "8", "25.0°C", "85%", "Cloudy"));
        weatherDataList.add(new WeatherData("Tue", "9", "27.5°C", "83%", "Partly cloudy"));
        weatherDataList.add(new WeatherData("Wed", "10", "28.5°C", "86%", "Partly cloudy"));
        weatherDataList.add(new WeatherData("Thu", "11", "29.0°C", "84%", "Partly cloudy"));
        weatherDataList.add(new WeatherData("Fri", "12", "29.5°C", "82%", "Scattered thunderstorms"));
        weatherDataList.add(new WeatherData("Sat", "13", "27.5°C", "80%", "Thunderstorms"));
        weatherDataList.add(new WeatherData("Sun", "14", "27.0°C", "78%", "Heavy rain"));
        weatherDataList.add(new WeatherData("Mon", "15", "26.5°C", "75%", "Heavy rain"));
        weatherDataList.add(new WeatherData("Tue", "16", "27.0°C", "73%", "Light rain"));
        weatherDataList.add(new WeatherData("Wed", "17", "27.5°C", "71%", "Cloudy"));
        weatherDataList.add(new WeatherData("Thu", "18", "28.0°C", "70%", "Partly cloudy"));
        weatherDataList.add(new WeatherData("Fri", "19", "28.5°C", "72%", "Partly cloudy"));
        weatherDataList.add(new WeatherData("Sat", "20", "27.5°C", "74%", "Sunny"));
        weatherDataList.add(new WeatherData("Sun", "21", "28.5°C", "76%", "Sunny"));
        weatherDataList.add(new WeatherData("Mon", "22", "29.0°C", "78%", "Sunny"));
        weatherDataList.add(new WeatherData("Tue", "23", "29.5°C", "80%", "Partly cloudy"));
        weatherDataList.add(new WeatherData("Wed", "24", "29.0°C", "82%", "Partly cloudy"));
        weatherDataList.add(new WeatherData("Thu", "25", "28.5°C", "84%", "Scattered thunderstorms"));
        weatherDataList.add(new WeatherData("Fri", "26", "28.0°C", "86%", "Thunderstorms"));
        weatherDataList.add(new WeatherData("Sat", "27", "27.5°C", "88%", "Heavy rain"));
        weatherDataList.add(new WeatherData("Sun", "28", "27.0°C", "90%", "Heavy rain"));
        weatherDataList.add(new WeatherData("Mon", "29", "26.5°C", "92%", "Light rain"));
        weatherDataList.add(new WeatherData("Tue", "30", "26.0°C", "94%", "Cloudy"));
        weatherDataList.add(new WeatherData("Wed", "31", "27.5°C", "96%", "Partly cloudy"));
        return weatherDataList;
    }

    private void setupWebView() {
        WebSettings webSettings = webViewWeatherMap.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        webViewWeatherMap.setWebViewClient(new WebViewClient());

        // Initial map load
        loadWeatherMap("https://tile.openweathermap.org/map/temp_new/1/1/1.png?appid=dd6831d90c1ef8ad63e0c2fefae7f847", "Temperature Map", "Shows the current temperature across different regions. \n\nColor Legend: \n- Blue: Cold temperatures\n- Green: Mild temperatures\n- Yellow: Warm temperatures\n- Red: Hot temperatures");

        webViewWeatherMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://openweathermap.org"));
                startActivity(browserIntent);
            }
        });
    }

    private void setupSpinner() {
        final List<String> mapNames = new ArrayList<>();
        mapNames.add("Temperature Map");
        mapNames.add("Precipitation Map");
        mapNames.add("Wind Speed Map");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, mapNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMapSelector.setAdapter(adapter);

        spinnerMapSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedMap = mapNames.get(position);
                String url = "";
                String legend = "";

                switch (selectedMap) {
                    case "Temperature Map":
                        url = "https://tile.openweathermap.org/map/temp_new/1/1/1.png?appid=dd6831d90c1ef8ad63e0c2fefae7f847";
                        legend = "Shows the current temperature across different regions. \n\nColor Legend: \n- Blue: Cold temperatures\n- Yellow: Mild temperatures\n- Orange: Warm temperatures\n- Red: Hot temperatures";
                        break;
                    case "Precipitation Map":
                        url = "https://tile.openweathermap.org/map/precipitation_new/1/1/1.png?appid=dd6831d90c1ef8ad63e0c2fefae7f847";
                        legend = "Shows the current precipitation levels, indicating rain, snow, etc. \n\nColor Legend: \n- White: Light precipitation\n- Blue: Moderate precipitation\n- Dark Blue: Heavy precipitation";
                        break;
                    case "Wind Speed Map":
                        url = "https://tile.openweathermap.org/map/wind_new/1/1/1.png?appid=dd6831d90c1ef8ad63e0c2fefae7f847";
                        legend = "Shows the current wind speeds across different regions. \n\nColor Legend: \n- White : Light winds\n- Light Purple: Moderate winds\n- Purple: Strong winds\n- Dark Purple: Very strong winds";
                        break;
                }

                loadWeatherMap(url, selectedMap, legend);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void loadWeatherMap(String url, String mapName, String legend) {
        webViewWeatherMap.loadUrl(url);
        textViewMapName.setText(mapName);
        textViewMapLegend.setText(legend);
    }
}
