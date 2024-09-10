package com.example.farmiq.network;

import com.example.farmiq.models.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("weather")
    Call<WeatherResponse> getWeather(@Query("q") String city, @Query("units") String units, @Query("appid") String apiKey);
}