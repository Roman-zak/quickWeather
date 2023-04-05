package com.weather.api.services;

import com.weather.api.models.Coordinates;
import com.weather.api.models.Weather;
import org.springframework.stereotype.Service;

@Service
public interface WeatherService {
    public Weather getCurrentWeather(Coordinates coordinates);
}
