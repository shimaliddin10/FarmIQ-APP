package com.example.farmiq;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.farmiq.models.WeatherResponse;
import com.example.farmiq.network.ApiClient;
import com.example.farmiq.network.WeatherApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private TextView weatherInfo;
    private LottieAnimationView weatherAnimation;
    private ImageView soilTestImage;
    private Button cropRecommendationButton;
    private ImageView sellImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        weatherInfo = view.findViewById(R.id.weather_info);
        weatherAnimation = view.findViewById(R.id.weather_animation);
        soilTestImage = view.findViewById(R.id.soiltest_image);
        cropRecommendationButton = view.findViewById(R.id.crop_recommendation_button);
        sellImage = view.findViewById(R.id.sell1);

        getWeatherData();

        soilTestImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSoilTestFragment();
            }
        });

        cropRecommendationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCropRecommendationFragment();
            }
        });

        sellImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSellFragment();
            }
        });

        return view;
    }

    private void getWeatherData() {
        WeatherApiService apiService = ApiClient.getClient().create(WeatherApiService.class);
        Call<WeatherResponse> call = apiService.getWeather("Mangalore", "metric", "5394d54b948550f8af85efa6e913718e");
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    WeatherResponse weatherResponse = response.body();
                    String weatherText = "City: " + weatherResponse.name + "\n" +
                            "Temperature: " + weatherResponse.main.temp + "°C\n" +
                            "Humidity: " + weatherResponse.main.humidity + "%\n" +
                            "Condition: " + weatherResponse.weather.get(0).description;
                    weatherInfo.setText(weatherText);

                    String condition = weatherResponse.weather.get(0).description.toLowerCase();
                    if (condition.contains("rain")) {
                        weatherAnimation.setAnimation(R.raw.rain_animation);
                    } else if (condition.contains("clear")) {
                        weatherAnimation.setAnimation(R.raw.clear_animation);
                    } else if (condition.contains("cloud")) {
                        weatherAnimation.setAnimation(R.raw.cloud_animation);
                    } else if (condition.contains("snow")) {
                        weatherAnimation.setAnimation(R.raw.snow_animation);
                    } else {
                        weatherAnimation.setAnimation(R.raw.default_weather_animation);
                    }
                    weatherAnimation.playAnimation();
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                weatherInfo.setText("Failed to get weather data");
                weatherAnimation.setAnimation(R.raw.error_animation);
                weatherAnimation.playAnimation();
            }
        });
    }

    private void openSoilTestFragment() {
        SoilTestFragment soilTestFragment = new SoilTestFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, soilTestFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void openCropRecommendationFragment() {
        CropRecommendationFragment cropRecommendationFragment = new CropRecommendationFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, cropRecommendationFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void openSellFragment() {
        SellFragment sellFragment = new SellFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, sellFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
