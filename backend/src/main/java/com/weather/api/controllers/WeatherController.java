package com.weather.api.controllers;

import com.weather.api.models.Weather;
import com.weather.api.models.Coordinates;
import com.weather.api.services.WeatherService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Validated
public class WeatherController {
    @Autowired
    WeatherService openMeteoWeatherService;

    public WeatherController(WeatherService openMeteoWeatherService) {
        this.openMeteoWeatherService = openMeteoWeatherService;
    }

    //TODO exception handling, return 400 on validation exception
    @GetMapping("/currentWeather")
    public Weather weather(@Valid @RequestParam @DecimalMin("-90.0") @DecimalMax("90.0") double latitude,
                           @Valid @RequestParam @DecimalMin("-180.0") @DecimalMax(value = "180.0", inclusive = false) double longitude) {
        return openMeteoWeatherService.getCurrentWeather(new Coordinates(latitude, longitude));
    }
    @ExceptionHandler({ConstraintViolationException.class})
    public void handleException(HttpServletResponse response, ConstraintViolationException ex) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(),ex.getLocalizedMessage());
    }
}
