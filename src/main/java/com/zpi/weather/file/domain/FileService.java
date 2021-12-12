package com.zpi.weather.file.domain;

import com.zpi.weather.analyzer.weather.domain.WeatherRepository;
import com.zpi.weather.analyzer.weather.domain.entity.DateEntity;
import com.zpi.weather.analyzer.weather.domain.entity.WeatherEntity;
import com.zpi.weather.analyzer.weatherStation.domain.WeatherStationRepository;
import com.zpi.weather.analyzer.weatherStation.domain.entity.WeatherStationEntity;
import com.zpi.weather.file.domain.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Date;

@Service
public class FileService {

    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;
    private final WeatherRepository weatherRepository;
    private final WeatherStationRepository weatherStationRepository;


    public FileService(WeatherRepository weatherRepository, WeatherStationRepository weatherStationRepository) {
        this.weatherRepository = weatherRepository;
        this.weatherStationRepository = weatherStationRepository;
    }

    public void uploadFile(MultipartFile file) {
        try {
            Path copyLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
            addDataToDatabase(Path.of(uploadDir + File.separator + file.getOriginalFilename()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file " + file.getOriginalFilename()
                    + ". Please try again!");
        }
    }

    private void addDataToDatabase(Path path) throws IOException {
        Files.lines(path).skip(1).forEach(line -> {
            String[] splitData = splitLine(line);
            try {
                WeatherLineValidator.validateLine(splitData);
                WeatherStationEntity station = weatherStationRepository.findByCode(splitData[6]);
                station = createStationIfNotExists(station, splitData);
                createInformationAboutWeather(station, splitData);
            } catch (Exception e) {
                addErrorLine(line, path);
            }
        });
    }

    private void addErrorLine(String line, Path path) {
        try {
            Files.write(Path.of(path + ".fail"), line.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println(line);
        } catch (Exception ex) {
            System.out.println("Fail: " + line);
        }
    }

    private void createInformationAboutWeather(WeatherStationEntity station, String[] splitData) {
        Date date = new Date(Integer.parseInt(splitData[4]), Integer.parseInt(splitData[2]) - 1, Integer.parseInt(splitData[3]));
        weatherRepository.save(WeatherEntity.builder()
                .date(DateEntity.builder()
                        .date(date)
                        .build())
                .weatherStation(station)
                .temperatureAvg(Integer.parseInt(splitData[9]))
                .temperatureMin(Integer.parseInt(splitData[11]))
                .temperatureMax(Integer.parseInt(splitData[10]))
                .precipitation(Double.parseDouble(splitData[0]))
                .windDirection(Integer.parseInt(splitData[12]))
                .windSpeed(Double.parseDouble(splitData[13]))
                .build());
    }

    private String[] splitLine(String line) {
        String[] splitData = line.split("\",");
        for (int i = 0; i < splitData.length; i++) {
            splitData[i] = splitData[i].replace("\"", "");
        }
        return splitData;
    }

    private WeatherStationEntity createStationIfNotExists(WeatherStationEntity station, String[] splitData) {
        if (station == null) {
            return weatherStationRepository.save(WeatherStationEntity.builder()
                    .name(splitData[5])
                    .code(splitData[6])
                    .state(splitData[8])
                    .build());
        }
        return station;
    }
}
