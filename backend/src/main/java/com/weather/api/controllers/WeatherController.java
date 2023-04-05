package com.weather.api.controllers;

import com.weather.api.models.Coordinates;
import com.weather.api.models.Weather;
import com.weather.api.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = {"http://localhost:8000", "null"})
public class WeatherController {
    @Autowired
    WeatherService openMeteoWeatherService;

    @GetMapping("/currentWeather")
    public Weather weather(@RequestParam double latitude, @RequestParam double longitude) {
        return openMeteoWeatherService.getCurrentWeather(new Coordinates(latitude, longitude));
    }
}
