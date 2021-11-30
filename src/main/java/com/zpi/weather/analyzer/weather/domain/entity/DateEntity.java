package com.zpi.weather.analyzer.weather.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DateEntity {
    @Temporal(TemporalType.DATE)
    private Date date;
}
