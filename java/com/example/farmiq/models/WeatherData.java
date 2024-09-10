package com.example.farmiq.models;

public class WeatherData {
    private String day;
    private String date;
    private String temperature;
    private String humidity;
    private String description;
    private boolean isExpanded;

    public WeatherData(String day, String date, String temperature, String humidity, String description) {
        this.day = day;
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.description = description;
        this.isExpanded = false; // Initially collapsed
    }

    public String getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getDescription() {
        return description;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
