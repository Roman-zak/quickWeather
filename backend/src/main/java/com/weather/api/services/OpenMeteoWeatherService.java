package com.weather.api.services;

import com.weather.api.models.Coordinates;
import com.weather.api.models.Weather;
import com.weather.api.weatherclient.OpenMeteoWeatherClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OpenMeteoWeatherService implements WeatherService{
    private final OpenMeteoWeatherClient openMeteoWeatherClient;
    @Override
    public Weather getCurrentWeather(Coordinates coordinates) {
        Weather weather = openMeteoWeatherClient.getWeather(coordinates);
        log.trace(String.valueOf(weather));
        return weather;
    }
}
