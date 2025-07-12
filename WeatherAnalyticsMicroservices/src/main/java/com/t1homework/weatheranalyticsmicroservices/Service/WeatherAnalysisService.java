package com.t1homework.weatheranalyticsmicroservices.Service;


import com.t1homework.WeatherModel.WeatherEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class WeatherAnalysisService {

    private final List<WeatherEvent> allEvents = new ArrayList<>();
    private final Map<String, List<WeatherEvent>> eventsByCity = new HashMap<>();

    public synchronized void addEvent(WeatherEvent event) {
        allEvents.add(event);

        String city = event.getCity();
        eventsByCity
                .computeIfAbsent(city, k -> new ArrayList<>())
                .add(event);
    }

    // Общий анализ
    public Map<String, Object> getGlobalAnalysis() {
        return buildAnalysis(allEvents);
    }

    // Анализ по конкретному городу
    public Map<String, Object> getCityAnalysis(String city) {
        List<WeatherEvent> cityEvents = eventsByCity.get(city);
        if (cityEvents == null || cityEvents.isEmpty()) {
            return Map.of("message", "Нет данных по городу " + city);
        }
        return buildAnalysis(cityEvents);
    }

    private Map<String, Object> buildAnalysis(List<WeatherEvent> events) {
        double avgTemp = events.stream()
                .mapToDouble(WeatherEvent::getTemperature)
                .average()
                .orElse(0.0);

        double maxTemp = events.stream()
                .mapToDouble(WeatherEvent::getTemperature)
                .max()
                .orElse(0.0);

        double minTemp = events.stream()
                .mapToDouble(WeatherEvent::getTemperature)
                .min()
                .orElse(0.0);

        Map<String, Object> analysis = new LinkedHashMap<>();
        analysis.put("Кол-во данных", events.size());
        analysis.put("Средняя темпиратура", avgTemp);
        analysis.put("Максимальная темпиратура", maxTemp);
        analysis.put("Минимальная темпиратура", minTemp);

        return analysis;
    }

    public void printAnalysis() {
        log.info("=== 📊 Общий анализ ===");
        log.info("{}", getGlobalAnalysis());

        log.info("=== 🏙️ Анализ по городам ===");
        eventsByCity.keySet().forEach(city -> {
            log.info("Город: {}", city);
            log.info("{}", getCityAnalysis(city));
        });
    }

}