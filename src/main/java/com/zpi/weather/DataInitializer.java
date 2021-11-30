package com.zpi.weather;

import com.zpi.weather.analyzer.weather.domain.WeatherRepository;
import com.zpi.weather.analyzer.weather.domain.entity.DateEntity;
import com.zpi.weather.analyzer.weather.domain.entity.WeatherEntity;
import com.zpi.weather.analyzer.weatherStation.domain.WeatherStationRepository;
import com.zpi.weather.analyzer.weatherStation.domain.entity.WeatherStationEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

@Component
public class DataInitializer implements ApplicationRunner {
    private final WeatherRepository weatherRepository;
    private final WeatherStationRepository weatherStationRepository;

    @Value( "${path.inputFiles}" )
    private String inputFilesPath;

    public DataInitializer(WeatherRepository weatherRepository, WeatherStationRepository weatherStationRepository) {
        this.weatherRepository = weatherRepository;
        this.weatherStationRepository = weatherStationRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Files.lines(Path.of(inputFilesPath + "weather_station.csv")).forEach(line -> {
                    String[] split = line.split(";");
                    weatherStationRepository.save(WeatherStationEntity.builder()
                            .name(split[0])
                            .code(split[1])
                            .state(split[2])
                            .build());
                }
        );

        Files.lines(Path.of(inputFilesPath + "weather_data.csv")).forEach( line -> {
            String[] split = line.split(";");
            WeatherStationEntity byCode = weatherStationRepository.findByCode(split[6]);
            Date date = new Date(Integer.parseInt(split[4]) - 1900, Integer.parseInt(split[2]) - 1, Integer.parseInt(split[3]));
            weatherRepository.save(WeatherEntity.builder()
                    .date(DateEntity.builder()
                            .date(date)
                            .build())
                    .weatherStation(byCode)
                    .temperatureAvg(Integer.parseInt(split[9]))
                    .temperatureMin(Integer.parseInt(split[11]))
                    .temperatureMax(Integer.parseInt(split[10]))
                    .precipitation(Double.parseDouble(split[0]))
                    .windDirection(Integer.parseInt(split[12]))
                    .windSpeed(Double.parseDouble(split[13]))
                    .build());
        });
    }
}
