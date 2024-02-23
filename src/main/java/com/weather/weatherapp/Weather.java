package com.weather.weatherapp;

public class Weather {

    private String name;
    private double temperature;
    private double wind;

    public Weather(String name, double temperature, double wind) {
        this.name = name;
        this.temperature = temperature;
        this.wind = wind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }
}
