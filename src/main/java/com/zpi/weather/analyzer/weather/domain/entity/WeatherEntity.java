package com.zpi.weather.analyzer.weather.domain.entity;

import com.zpi.weather.analyzer.weatherStation.domain.entity.WeatherStationEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "weather")
public class WeatherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long weatherId;
    double precipitation;
    int temperatureMin;
    int temperatureMax;
    int temperatureAvg;
    int windDirection;
    double windSpeed;

    @Embedded
    DateEntity date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "weather_station_id")
    WeatherStationEntity weatherStation;
}
