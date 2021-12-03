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

  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @GetMapping("/information")
  public ResponseEntity<List<WeatherDto>> getInformationAboutWeather(@RequestParam(required = false) String numberOfPage,
                                                                     @RequestParam(required = false) String size,
                                                                     @RequestParam(required = false) String sortBy) {
    return ResponseEntity.ok(weatherService.getWeatherInformation(numberOfPage, size, sortBy));
  }
  @CrossOrigin(origins = "*", allowedHeaders = "*")
  @PostMapping("/information")
  public ResponseEntity<WeatherEntity> createWeatherEntry(@RequestBody WeatherBasicDto weatherDto) {
    WeatherEntity weatherEntry = weatherService.createWeatherEntry(weatherDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(weatherEntry);
  }
}
