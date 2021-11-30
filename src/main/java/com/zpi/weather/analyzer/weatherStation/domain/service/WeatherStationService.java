package com.zpi.weather.analyzer.weatherStation.domain.service;

import com.zpi.weather.analyzer.weatherStation.domain.entity.WeatherStationEntity;
import com.zpi.weather.analyzer.weatherStation.dto.WeatherStationDto;

public interface WeatherStationService {
    WeatherStationEntity getWeatherStation(String code);
    void createWeatherStation(WeatherStationDto weatherStationDto);
}
