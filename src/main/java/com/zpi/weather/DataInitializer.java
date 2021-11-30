package com.zpi.weather;

import com.zpi.weather.analyzer.weather.domain.WeatherRepository;
import com.zpi.weather.analyzer.weather.domain.entity.DateEntity;
import com.zpi.weather.analyzer.weather.domain.entity.WeatherEntity;
import com.zpi.weather.analyzer.weatherStation.domain.WeatherStationRepository;
import com.zpi.weather.analyzer.weatherStation.domain.entity.WeatherStationEntity;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataInitializer implements ApplicationRunner {
    private final WeatherRepository weatherRepository;
    private final WeatherStationRepository weatherStationRepository;

    public DataInitializer(WeatherRepository weatherRepository, WeatherStationRepository weatherStationRepository) {
        this.weatherRepository = weatherRepository;
        this.weatherStationRepository = weatherStationRepository;
    }

//    @Override
//    public void run(String... args) throws Exception {
//        Map<String, String> stations = new HashMap<>();
//        stations.put("BHM", "Birmingham");
//        stations.put("HSV", "Huntsville");
//        stations.put("MOB", "Mobile");
//
//        stations.forEach( (k,v) -> weatherStationRepository.save(WeatherStationEntity.builder().name(v).code(k).build()));

//        WeatherEntity weather = WeatherEntity.builder()
//                .date(DateEntity.builder()
//                        .date(new Date())
//                        .build())
//                .weatherStation(weatherStationRepository.findByCode("BHM"))
//                .temperatureAvg(17)
//                .temperatureMin(11)
//                .temperatureMax(23)
//                .precipitation(232.32)
//                .windDirection(123)
//                .windSpeed(213.322)
//                .build();
//        WeatherEntity weather_2 = WeatherEntity.builder()
//                .date(DateEntity.builder()
//                        .date(new Date(2004, Calendar.JANUARY, 10))
//                        .build())
//                .weatherStation(weatherStationRepository.findByCode("BHM"))
//                .temperatureAvg(16)
//                .temperatureMin(8)
//                .temperatureMax(25)
//                .precipitation(22.76)
//                .windDirection(65)
//                .windSpeed(65.73)
//                .build();
//        WeatherEntity weather_3 = WeatherEntity.builder()
//                .date(DateEntity.builder()
//                        .date(new Date(2016, Calendar.MAY, 12))
//                        .build())
//                .weatherStation(weatherStationRepository.findByCode("MOB"))
//                .temperatureAvg(12)
//                .temperatureMin(-10)
//                .temperatureMax(23)
//                .precipitation(25.4)
//                .windDirection(23)
//                .windSpeed(5.44)
//                .build();
//        weatherRepository.save(weather);
//        weatherRepository.save(weather_2);
//        weatherRepository.save(weather_3);
//    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Files.lines(Path.of("/Volumes/Dane/STUDIA/Zarządzanie_projektem/projekt/weather_station.csv")).forEach(line -> {
                    String[] split = line.split(";");
                    weatherStationRepository.save(WeatherStationEntity.builder()
                            .name(split[0])
                            .code(split[1])
                            .state(split[2])
                            .build());
                }
        );
        Files.lines(Path.of("/Volumes/Dane/STUDIA/Zarządzanie_projektem/projekt/weather_data.csv")).forEach( line -> {
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

//    public void createWeatherStation() {
//        Map<String, String> stations = new HashMap<>();
//        stations.put("BHM", "Birmingham");
//        stations.put("HSV", "Huntsville");
//        stations.put("MOB", "Mobile");
//
//        stations.forEach( (k,v) -> weatherStationRepository.save(WeatherStationEntity.builder().name(v).code(k).build()));
//    }

//    public void createWeatherEntry() {
//        WeatherEntity weather = WeatherEntity.builder()
//                .date(DateEntity.builder()
//                        .date(new Date())
//                        .build())
//                .weatherStation(weatherStationRepository.findByCode("BHM"))
//                .temperatureAvg(17)
//                .temperatureMin(11)
//                .temperatureMax(23)
//                .precipitation(232.32)
//                .windDirection(123)
//                .windSpeed(213.322)
//                .build();
//        WeatherEntity weather_2 = WeatherEntity.builder()
//                .date(DateEntity.builder()
//                        .date(new Date(2004, Calendar.JANUARY, 10))
//                        .build())
//                .weatherStation(weatherStationRepository.findByCode("BHM"))
//                .temperatureAvg(16)
//                .temperatureMin(8)
//                .temperatureMax(25)
//                .precipitation(22.76)
//                .windDirection(65)
//                .windSpeed(65.73)
//                .build();
//        WeatherEntity weather_3 = WeatherEntity.builder()
//                .date(DateEntity.builder()
//                        .date(new Date(2016, Calendar.MAY, 12))
//                        .build())
//                .weatherStation(weatherStationRepository.findByCode("MOB"))
//                .temperatureAvg(12)
//                .temperatureMin(-10)
//                .temperatureMax(23)
//                .precipitation(25.4)
//                .windDirection(23)
//                .windSpeed(5.44)
//                .build();
//        weatherRepository.save(weather);
//        weatherRepository.save(weather_2);
//        weatherRepository.save(weather_3);
//    }
}
