package com.weather.api.models;

import lombok.*;

import java.time.LocalDateTime;
// LocalDateTime, encoding
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Weather {
    private Coordinates coordinates;
    private double temperature;
    private double windspeed;
    private double winddirection;
    private String description;
    private LocalDateTime time;
}
