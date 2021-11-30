package com.zpi.weather.analyzer.weatherStation;

import com.zpi.weather.analyzer.weatherStation.domain.service.WeatherStationService;
import com.zpi.weather.analyzer.weatherStation.dto.WeatherStationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/weather/station")
public class WeatherStationController {

    private final WeatherStationService weatherStationService;

    public WeatherStationController(WeatherStationService weatherStationService) {
        this.weatherStationService = weatherStationService;
    }

    @PostMapping
    public void addWeatherStation(WeatherStationDto weatherStationDto) {
        weatherStationService.createWeatherStation(weatherStationDto);
    }
}
