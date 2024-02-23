package com.weather.weatherapp;

public class Forecast {

    private int day;
    private double temperature;
    private int wind;

    public Forecast(int day, double temperature, int wind) {
        this.day = day;
        this.temperature = temperature;
        this.wind = wind;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
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
}
