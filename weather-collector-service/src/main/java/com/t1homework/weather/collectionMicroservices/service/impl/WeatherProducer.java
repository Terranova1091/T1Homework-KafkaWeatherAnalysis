package com.t1homework.weather.collectionMicroservices.service.impl;

import com.t1homework.WeatherModel.WeatherEvent;
import com.t1homework.weather.collectionMicroservices.domain.Weather.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static java.util.Map.entry;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherProducer {

    private final KafkaTemplate<String, WeatherEvent> kafkaTemplate;
    private final Random random = new Random();

    private static final Map<String, String> CITIES = Map.ofEntries(
            entry("Санкт-Петербург", "Europe/Moscow"),
            entry("Магадан", "Asia/Magadan"),
            entry("Тюмень", "Asia/Yekaterinburg"),
            entry("Чукотка", "Asia/Kamchatka")

    );

    @Scheduled(fixedRate = 10000)
    public void generateAndSendWeatherEvent(){
        WeatherEvent event = generateWeatherEvent();
        kafkaTemplate.send("weather-topic", event.getCity(), event);
        log.info("Отправлено: {}", event);
    }

    private WeatherEvent generateWeatherEvent() {

        String city = getRandomCity();
        return WeatherEvent.create(
                UUID.randomUUID().toString(),
                city,
                getRandomStatus(),
                getRandomTemperatureCalculation(city),
                getCityZonedDateTime(city)
        );

    }

    private String getRandomCity(){
        return CITIES.keySet().stream()
                .skip(random.nextInt(CITIES.size()))
                .findFirst()
                .orElseThrow();
    }

    private String getRandomStatus() {
        return Arrays.stream(Status.values())
                .skip(random.nextInt(Status.values().length))
                .findFirst()
                .map(Status::name)
                .orElse("SUNNY");
    }

    private double getRandomTemperatureCalculation(String city){
        Map<String, Double[]> cityTemperatureRanges = Map.of(
                "Санкт-Петербург", new Double[]{-12.0, 22.0},
                "Магадан", new Double[]{0.0, 15.0},
                "Тюмень", new Double[]{0.0, 28.0},
                "Чукотка", new Double[]{0.0, 25.0}
        );

        Double[] range = cityTemperatureRanges.getOrDefault(city, new Double[]{0.0, 30.0});
        double minTemp = range[0];
        double maxTemp = range[1];

        Random random = new Random();
        double temperature = minTemp + (maxTemp - minTemp) * random.nextDouble();

        return Math.round(temperature * 10) / 10.0;
    }

    public ZonedDateTime getCityZonedDateTime(String city) {
        String timeZoneId = CITIES.getOrDefault(city, "UTC");
        return ZonedDateTime.now(ZoneId.of(timeZoneId));
    }

}
