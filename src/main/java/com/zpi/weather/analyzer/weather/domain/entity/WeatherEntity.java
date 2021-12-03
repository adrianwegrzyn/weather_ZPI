package com.zpi.weather.analyzer.weather.domain.entity;

import com.zpi.weather.analyzer.weather.dto.WeatherDto;
import com.zpi.weather.analyzer.weatherStation.domain.entity.WeatherStationEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public WeatherDto toDto() {
        return  WeatherDto.builder()
            .date(date.getDate())
            .month(separateDate(date.getDate(), Calendar.MONTH))
            .year(separateDate(date.getDate(), Calendar.YEAR))
            .weekOf(separateDate(date.getDate(), Calendar.DAY_OF_WEEK))
            .weatherStationCode(weatherStation.getCode())
            .weatherStationName(weatherStation.getName())
            .weatherStationState(weatherStation.getState())
            .precipitation(precipitation)
            .temperatureAvg(temperatureAvg)
            .temperatureMax(temperatureMax)
            .temperatureMin(temperatureMin)
            .windDirection(windDirection)
            .windSpeed(windSpeed)
            .build();
    }

    private int separateDate(Date date, int partDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(partDate);
    }
}
