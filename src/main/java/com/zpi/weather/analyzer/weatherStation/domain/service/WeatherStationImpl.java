package com.zpi.weather.analyzer.weatherStation.domain.service;

import com.zpi.weather.analyzer.weatherStation.domain.WeatherStationRepository;
import com.zpi.weather.analyzer.weatherStation.domain.entity.WeatherStationEntity;
import com.zpi.weather.analyzer.weatherStation.dto.WeatherStationDto;
import org.springframework.stereotype.Service;

@Service
public class WeatherStationImpl implements WeatherStationService {
    private final WeatherStationRepository weatherStationRepository;

    public WeatherStationImpl(WeatherStationRepository weatherStationRepository) {
        this.weatherStationRepository = weatherStationRepository;
    }

    @Override
    public WeatherStationEntity getWeatherStation(String code) {
        return weatherStationRepository.findByCode(code);
    }

    @Override
    public void createWeatherStation(WeatherStationDto weatherStationDto) {
        weatherStationRepository.save(WeatherStationEntity.builder()
                .name(weatherStationDto.getName())
                .code(weatherStationDto.getCode())
                .state(weatherStationDto.getState())
                .build());
    }
}
