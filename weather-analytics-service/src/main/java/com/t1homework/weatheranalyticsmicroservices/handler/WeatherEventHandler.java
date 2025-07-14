package com.t1homework.weatheranalyticsmicroservices.handler;


import com.t1homework.WeatherModel.WeatherEvent;
import com.t1homework.weatheranalyticsmicroservices.Service.WeatherAnalysisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@KafkaListener(topics = "weather-topic")
@Slf4j
@RequiredArgsConstructor
public class WeatherEventHandler {

    private final WeatherAnalysisService analysisService;

    @KafkaHandler
    public void handle(WeatherEvent weatherEvent ){
        log.debug("Пришел ивент: {}", weatherEvent);

        analysisService.addEvent(weatherEvent);

        analysisService.printAnalysis();
    }

    // Обработка других ивентов... (Не предусмотрено заданием)
}
