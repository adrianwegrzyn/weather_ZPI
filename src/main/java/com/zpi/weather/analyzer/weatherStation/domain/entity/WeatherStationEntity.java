package com.zpi.weather.analyzer.weatherStation.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zpi.weather.analyzer.weather.domain.entity.WeatherEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "weather_station")
public class WeatherStationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long weatherStationId;
    String name;
    String code;
    String state;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "weatherStation")
    @JsonIgnore
    Set<WeatherEntity> weathers;
}
