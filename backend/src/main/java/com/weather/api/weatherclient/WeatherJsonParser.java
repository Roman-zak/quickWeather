package com.weather.api.weatherclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.api.models.Coordinates;
import com.weather.api.models.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
public class WeatherJsonParser {
    public Weather parseWeather(JsonNode bodyNode) {
        Weather weather = new Weather();
        weather.setCoordinates(new Coordinates(bodyNode.get("latitude").asDouble(), bodyNode.get("longitude").asDouble()));
        Optional<JsonNode> currentWeatherNode = getCurrentWeatherNode(bodyNode);
        try {
            currentWeatherNode.ifPresentOrElse(weatherNode -> {
                weather.setWindspeed(weatherNode.get("windspeed").asDouble());
                weather.setWinddirection(weatherNode.get("winddirection").asDouble());
                weather.setTemperature(weatherNode.get("temperature").asDouble());
                weather.setDescription(interpretWeatherCode(weatherNode.get("weathercode").asInt()));
                weather.setTime(LocalDateTime.parse(weatherNode.get("time").asText()));
            }, currentWeatherNode::orElseThrow);
        } catch (NoSuchElementException | NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
        return weather;
    }

    private static Optional<JsonNode> getCurrentWeatherNode(JsonNode rootNode) {
        return Optional.ofNullable(rootNode.get("current_weather"));
    }

    private static String interpretWeatherCode(int code) {
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
