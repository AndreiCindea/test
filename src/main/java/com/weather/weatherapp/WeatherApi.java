package com.weather.weatherapp;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class WeatherApi {

    private double temperature;
    private int wind;
    private String description;
    private List<Forecast> forecast;

    public WeatherApi(double temperature, int wind, String description, List<Forecast> forecast) {
        this.temperature = temperature;
        this.wind = wind;
        this.description = description;
        this.forecast = forecast;
    }

    public WeatherApi() {

    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getWind() {
        return wind;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }

    public void setForecast(List<Forecast> forecast) {
        this.forecast = forecast;
    }
}
