package com.zpi.weather.analyzer.weather.domain.service;

import com.zpi.weather.analyzer.weather.domain.WeatherRepository;
import com.zpi.weather.analyzer.weather.domain.entity.DateEntity;
import com.zpi.weather.analyzer.weather.domain.entity.WeatherEntity;
import com.zpi.weather.analyzer.weather.dto.WeatherBasicDto;
import com.zpi.weather.analyzer.weather.dto.WeatherDto;
import com.zpi.weather.analyzer.weatherStation.domain.service.WeatherStationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherServiceImpl implements WeatherService {
  private final WeatherRepository weatherRepository;
  private final WeatherStationService weatherStationService;

  public WeatherServiceImpl(WeatherRepository weatherRepository, WeatherStationService weatherStationService) {
    this.weatherRepository = weatherRepository;
    this.weatherStationService = weatherStationService;
  }

  @Override
  public WeatherEntity createWeatherEntry(WeatherBasicDto weatherDto) {
    WeatherEntity weather = WeatherEntity.builder()
        .date(DateEntity.builder()
            .date(new Date())
            .build())
        .weatherStation(weatherStationService.getWeatherStation(weatherDto.getWeatherStationCode()))
        .temperatureAvg(weatherDto.getTemperatureAvg())
        .temperatureMin(weatherDto.getTemperatureMin())
        .temperatureMax(weatherDto.getTemperatureMax())
        .precipitation(weatherDto.getPrecipitation())
        .windDirection(weatherDto.getWindDirection())
        .windSpeed(weatherDto.getWindSpeed())
        .build();
    return weatherRepository.save(weather);
  }

  @Override
  public List<WeatherDto> getWeatherInformation(String numberOfPage, String size, String sortBy) {
    if (numberOfPage == null && size == null && sortBy == null) {
      return weatherRepository.findAll().stream()
          .map(WeatherEntity::toDto)
          .collect(Collectors.toList());
    }

    Pageable pageable = PageRequest.of(Integer.parseInt(numberOfPage), Integer.parseInt(size), Sort.by(sortBy));
    return weatherRepository.findAll(pageable).getContent().stream()
        .map(WeatherEntity::toDto)
        .collect(Collectors.toList());
  }
}
