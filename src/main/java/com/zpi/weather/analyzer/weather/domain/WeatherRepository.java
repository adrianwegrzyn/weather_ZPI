package com.zpi.weather.analyzer.weather.domain;

import com.zpi.weather.analyzer.weather.domain.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, Long> {
}
