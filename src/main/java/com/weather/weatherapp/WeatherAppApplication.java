package com.weather.weatherapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.ToDoubleFunction;

@SpringBootApplication
public class WeatherAppApplication {

	public static final String AVERAGE_FROM_FORECAST_CSV = "AverageFromForecast.csv";
	public static final String WEATHER_TEMPERATURE_AND_AVERAGE_CSV = "WeatherTemperatureAndAverage.csv";

	public static void main(String[] args) {
		SpringApplication.run(WeatherAppApplication.class, args);

		String cities = "Cluj-Napoca,Bucuresti,Craiova,Timisoara,Dej,Constanta,Baia-Mare,Arad,Bistrita,Oradea";
		List<String> cityList = Arrays.asList(cities.split(","));
		RestTemplate restTemplate = new RestTemplate();
		WeatherService weatherService = new WeatherService(restTemplate);
		List<Weather> weathersForecastList = new ArrayList<>();
		List<Weather> weatherList = new ArrayList<>();

		cityList.forEach(city -> {
			WeatherApi weatherResponse = fetchDataFromApi(city,weatherService);
			weathersForecastList.add(getAverageForForecastListFromEveryCity(city, weatherResponse));
			weatherList.add(new Weather(city, weatherResponse.getTemperature(), weatherResponse.getWind()));
		});


		getAverageForCitites(weatherList);

		//generate from the forcast of every city and display the average for 6 days
		System.out.println("Average from forecast for every city");
		weathersForecastList.sort(Comparator.comparing(Weather::getName));
		generateJsonAndCsvFormat(weathersForecastList, AVERAGE_FROM_FORECAST_CSV);

		System.out.println("-----------------------------------");

		//generate for all the cities
		System.out.println("Average for Temperature and Wind for all the cities");
		generateJsonAndCsvFormat(weatherList, WEATHER_TEMPERATURE_AND_AVERAGE_CSV);
	}

	private static void getAverageForCitites(List<Weather> weatherList) {
		double temperature = calculateAverage(weatherList, Weather::getTemperature);
		double wind = calculateAverage(weatherList, Weather::getWind);
		weatherList.sort(Comparator.comparing(Weather::getName));

		weatherList.add(new Weather("Average", temperature, wind));
	}

	private static Weather getAverageForForecastListFromEveryCity(String city, WeatherApi weatherResponse) {
		List<Forecast> forecast = weatherResponse.getForecast();
		if(forecast == null) {
			return new Weather(city, 0.0, 0.0);
		}

		double temperature = calculateAverage(forecast, Forecast::getTemperature);
		double wind = calculateAverage(forecast, Forecast::getWind);

		return new Weather(city, temperature, wind);
	}

	private static void generateJsonAndCsvFormat(List<Weather> weathers, String csvFileName) {

		convertListToJson(weathers);

		generateCsv(csvFileName, weathers);
	}

	private static void generateCsv(String csvFileName, List<Weather> weathers) {
		try{
			FileWriter writer = new FileWriter(csvFileName);
			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Name", "Temperature", "Wind"));

			for (Weather weather : weathers) {
				csvPrinter.printRecord(weather.getName(), weather.getTemperature(), weather.getWind());
			}

			csvPrinter.flush();
			System.out.println(csvFileName + " file generated with success");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void convertListToJson(List<Weather> weathers) {
		try{
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(weathers);

			System.out.println("result: ");
			System.out.println(json);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private static WeatherApi fetchDataFromApi(String city, WeatherService weatherService) {
		try{
			return weatherService.fetchWeatherFromApi(city).getBody();
		} catch (HttpClientErrorException e) {
            return new WeatherApi();
        }

    }

	private static <T> double calculateAverage(List<T> list, ToDoubleFunction<T> mapper) {
		OptionalDouble average = list.stream()
				.mapToDouble(mapper)
				.average();

		return average.orElse(Double.NaN);
	}

}
