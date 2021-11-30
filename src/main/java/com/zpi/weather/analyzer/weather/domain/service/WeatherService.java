package com.zpi.weather.analyzer.weather.domain.service;

import com.zpi.weather.analyzer.weather.domain.entity.WeatherEntity;
import com.zpi.weather.analyzer.weather.dto.WeatherBasicDto;
import com.zpi.weather.analyzer.weather.dto.WeatherDto;

import java.util.List;

public interface WeatherService {
    WeatherEntity createWeatherEntry(WeatherBasicDto weatherDto);
    List<WeatherDto> getWeatherInformation(String numberOfPage, String size, String sortBy);
}
