package com.zpi.weather.analyzer.weather.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherDto {
    double precipitation;
    int temperatureMin;
    int temperatureMax;
    int temperatureAvg;
    int windDirection;
    double windSpeed;
    Date date;
    int month;
    int weekOf;
    int year;
    String weatherStationName;
    String weatherStationCode;
    String weatherStationState;
}
