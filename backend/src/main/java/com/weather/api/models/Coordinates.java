package com.weather.api.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Coordinates{
    private double latitude;
    private double longitude;
}
