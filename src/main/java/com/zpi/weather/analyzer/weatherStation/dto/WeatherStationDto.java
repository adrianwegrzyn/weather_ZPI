package com.zpi.weather.analyzer.weatherStation.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WeatherStationDto {
    String name;
    String code;
    String state;
}
