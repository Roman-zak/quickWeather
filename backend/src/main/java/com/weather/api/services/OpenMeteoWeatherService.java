package com.weather.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.api.models.Coordinates;
import com.weather.api.models.Weather;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.logging.log4j.LogManager;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OpenMeteoWeatherService implements WeatherService{
    private static final String WEATHER_RESOURCE_URL_TEMPLATE =
            "https://api.open-meteo.com/v1/forecast?latitude=%f&longitude=%f&current_weather=true&timezone=auto";
    private static final Logger LOGGER = LogManager.getLogger(OpenMeteoWeatherService.class);
    private final RestTemplate restTemplate;
    @Autowired
    public OpenMeteoWeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public Weather getCurrentWeather(Coordinates coordinates) {
        String weatherResourceUrl = getWeatherResourceUrl(coordinates);
        ResponseEntity<String> response = restTemplate.getForEntity(weatherResourceUrl, String.class);
        LOGGER.info(response);
        return getWeatherFromResponse(coordinates, response);
    }
    private static Weather getWeatherFromResponse(Coordinates coordinates, ResponseEntity<String> response) {
        Weather weather = new Weather(coordinates);
        JsonNode bodyNode = getJsonBodyNode(response);
        Optional<JsonNode> currentWeatherNode = getCurrentWeatherNode(bodyNode);
        try{
            currentWeatherNode.ifPresentOrElse(node -> {
                weather.setWindspeed(node.get("windspeed").asDouble());
                weather.setWinddirection(node.get("winddirection").asDouble());
                weather.setTemperature(node.get("temperature").asDouble());
                weather.setDescription(interpretWeatherCode(node.get("weathercode").asInt()));
                weather.setTime(LocalDateTime.parse(node.get("time").asText()));
            }, currentWeatherNode::orElseThrow);
        } catch ( NoSuchElementException | NumberFormatException e){
            LOGGER.error(e.getMessage(), e);
        }
        return weather;
    }

    private static Optional<JsonNode> getCurrentWeatherNode(JsonNode rootNode) {
        return Optional.ofNullable(rootNode.get("current_weather"));
    }

    private static JsonNode getJsonBodyNode(ResponseEntity<String> response) {
        JsonNode root = null;
        try {
            root = new ObjectMapper().readTree(response.getBody());
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return root;
    }

    private static String getWeatherResourceUrl(Coordinates coordinates) {
        return String.format(WEATHER_RESOURCE_URL_TEMPLATE, coordinates.getLatitude(), coordinates.getLongitude());
    }

    private static String interpretWeatherCode(int code){
        return switch (code) {
            case 0 -> "Clear sky";
            case 1 -> "Mainly clear";
            case 2 -> "Partly cloudy";
            case 3 -> "Overcast";
            case 45 -> "Fog";
            case 48 -> "Depositing rime fog";
            case 51 -> "Drizzle: Light intensity";
            case 53 -> "Drizzle: Moderate intensity";
            case 55 -> "Drizzle: Dense intensity";
            case 56 -> "Freezing Drizzle: Light intensity";
            case 57 -> "Freezing Drizzle: Dense intensity";
            case 61 -> "Rain: Slight intensity";
            case 63 -> "Rain: Moderate intensity";
            case 65 -> "Rain: Heavy intensity";
            case 66 -> "Freezing Rain: Light intensity";
            case 67 -> "Freezing Rain: Heavy intensity";
            case 71 -> "Snow fall: Slight intensity";
            case 73 -> "Snow fall: Moderate intensity";
            case 75 -> "Snow fall: Heavy intensity";
            case 77 -> "Snow grains";
            case 80 -> "Rain showers: Slight intensity";
            case 81 -> "Rain showers: Moderate intensity";
            case 82 -> "Rain showers: Violent intensity";
            case 85 -> "Snow showers: Slight intensity";
            case 86 -> "Snow showers: Heavy intensity";
            case 95 -> "Thunderstorm: Slight or moderate";
            case 96 -> "Thunderstorm with slight hail";
            case 99 -> "Thunderstorm with heavy hail";
            default -> throw new IllegalArgumentException("Unknown Weather code: " + code);
        };
    }

}
