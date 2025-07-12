package com.t1homework.weather.collectionMicroservices.domain.Weather;


import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class Weather {

    private String City;
    private Status Status;
    private Double Temperature;
    private ZonedDateTime LocalTime;
}
