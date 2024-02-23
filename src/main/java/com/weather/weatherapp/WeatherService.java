package com.weather.weatherapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WeatherService {
    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<WeatherApi> fetchWeatherFromApi(String city) {
        String apiUrl = "https://998d8129-2264-4a98-a92e-ba8bde4a4d1c.mock.pstmn.io/"+ city;
        return restTemplate.getForEntity(apiUrl, WeatherApi.class);
    }
}
