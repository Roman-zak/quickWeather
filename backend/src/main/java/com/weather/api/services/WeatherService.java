package com.weather.api.services;

import com.weather.api.models.Coordinates;
import com.weather.api.models.Weather;

public interface WeatherService {
    Weather getCurrentWeather(Coordinates coordinates);
}
