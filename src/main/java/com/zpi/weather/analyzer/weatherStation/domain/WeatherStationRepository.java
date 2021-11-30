package com.zpi.weather.analyzer.weatherStation.domain;

import com.zpi.weather.analyzer.weatherStation.domain.entity.WeatherStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherStationRepository extends JpaRepository<WeatherStationEntity, Long> {
    WeatherStationEntity findByCode(String code);
}
