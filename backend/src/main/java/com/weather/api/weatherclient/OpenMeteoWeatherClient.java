package com.weather.api.weatherclient;

import com.fasterxml.jackson.databind.JsonNode;
import com.weather.api.models.Coordinates;
import com.weather.api.models.Weather;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

public class OpenMeteoWeatherClient {
    private static final String WEATHER_RESOURCE_URL_TEMPLATE = "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current_weather=true";
    private final RestTemplate restTemplate;
    private final WeatherJsonParser weatherJsonParser;

    public OpenMeteoWeatherClient() {
        this(new RestTemplate());
    }

    public OpenMeteoWeatherClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.weatherJsonParser = new WeatherJsonParser();
    }

    public Weather getWeather(Coordinates coordinates) {
        String url = getWeatherResourceUrl(coordinates);
        JsonNode jsonNode = restTemplate.getForObject(url, JsonNode.class);
        Weather weather = weatherJsonParser.parseWeather(jsonNode);
        return weather;
    }

    private String getWeatherResourceUrl(Coordinates coordinates) {
        return String.format(Locale.US, WEATHER_RESOURCE_URL_TEMPLATE, coordinates.getLatitude(), coordinates.getLongitude());
    }

}
