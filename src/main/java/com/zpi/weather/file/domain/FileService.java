package com.zpi.weather.file.domain;

import com.zpi.weather.analyzer.weather.domain.WeatherRepository;
import com.zpi.weather.analyzer.weather.domain.entity.DateEntity;
import com.zpi.weather.analyzer.weather.domain.entity.WeatherEntity;
import com.zpi.weather.analyzer.weatherStation.domain.WeatherStationRepository;
import com.zpi.weather.analyzer.weatherStation.domain.entity.WeatherStationEntity;
import com.zpi.weather.file.domain.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        Files.lines(path).forEach(line -> {
            String[] split = line.split("\",");
            for(int i = 0; i < split.length; i++) {
                split[i] = split[i].replace("\"", "");
            }
            WeatherStationEntity byCode = weatherStationRepository.findByCode(split[6]);
            if (byCode == null) {
                byCode = weatherStationRepository.save(WeatherStationEntity.builder()
                        .name(split[5])
                        .code(split[6])
                        .state(split[8])
                        .build());
            }
            Date date = new Date(Integer.parseInt(split[4]), Integer.parseInt(split[2]) - 1, Integer.parseInt(split[3]));
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
