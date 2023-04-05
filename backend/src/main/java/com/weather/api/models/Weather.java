package com.weather.api.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Weather implements Serializable {
    private Coordinates coordinates;
    private double temperature;
    private double windspeed;
    private double winddirection;
    private String description;
    private LocalDateTime time;

    public Weather() {
    }

    public Weather(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Weather(Coordinates coordinates, double temperature, double windspeed, double winddirection, String description, LocalDateTime time) {
        this.coordinates = coordinates;
        this.temperature = temperature;
        this.windspeed = windspeed;
        this.winddirection = winddirection;
        this.description = description;
        this.time = time;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public double getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(double windspeed) {
        this.windspeed = windspeed;
    }

    public double getWinddirection() {
        return winddirection;
    }

    public void setWinddirection(double winddirection) {
        this.winddirection = winddirection;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "coordinates=" + coordinates +
                ", temperature=" + temperature +
                ", windspeed=" + windspeed +
                ", winddirection=" + winddirection +
                ", description='" + description + '\'' +
                ", time=" + time +
                '}';
    }
}
