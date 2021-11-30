package com.zpi.weather.analyzer.weather;

import com.zpi.weather.analyzer.weather.domain.entity.WeatherEntity;
import com.zpi.weather.analyzer.weather.domain.service.WeatherService;
import com.zpi.weather.analyzer.weather.dto.WeatherBasicDto;
import com.zpi.weather.analyzer.weather.dto.WeatherDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/weather")
public class AnalyzerController {

    private final WeatherService weatherService;

    public AnalyzerController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/hello")
    public String getMessage() {
        return "hello";
    }

    @GetMapping("/information")
    public ResponseEntity<List<WeatherDto>> getInformationAboutWeather(@RequestParam String numberOfPage, @RequestParam String sortBy) {
        return ResponseEntity.ok(weatherService.getWeatherInformation(numberOfPage, sortBy));
    }

    @PostMapping("/information")
    public ResponseEntity<WeatherEntity> createWeatherEntry(@RequestBody WeatherBasicDto weatherDto) {
        WeatherEntity weatherEntry = weatherService.createWeatherEntry(weatherDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(weatherEntry);
    }
}
