// WeatherResponse.java
package com.example.farmiq.models;

import java.util.List;

public class WeatherResponse {
    public Main main;
    public List<Weather> weather;
    public String name;

    public class Main {
        public float temp;
        public int humidity;
    }

    public class Weather {
        public String description;
    }
}
