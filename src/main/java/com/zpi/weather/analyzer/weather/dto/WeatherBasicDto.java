package com.zpi.weather.analyzer.weather.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherBasicDto {
    double precipitation;
    int temperatureMin;
    int temperatureMax;
    int temperatureAvg;
    int windDirection;
    double windSpeed;
    Date date;
    String weatherStationName;
    String weatherStationCode;
    String weatherStationState;
}
