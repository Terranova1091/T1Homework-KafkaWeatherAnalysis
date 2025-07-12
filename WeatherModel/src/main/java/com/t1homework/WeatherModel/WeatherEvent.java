package com.t1homework.WeatherModel;

import java.time.ZonedDateTime;


public class WeatherEvent {

    private String id;

    private String city;

    private String status;

    private double temperature;

    private ZonedDateTime zonedDateTime;

    public WeatherEvent() {
    }

    public WeatherEvent(String id, String city, String status,
                        double temperature, ZonedDateTime zonedDateTime) {
        this.id = id;
        this.city = city;
        this.status = status;
        this.temperature = temperature;
        this.zonedDateTime = zonedDateTime;
    }

    // Фабричный метод
    public static WeatherEvent create(String id, String city, String status,
                                      double temperature, ZonedDateTime zonedDateTime) {
        WeatherEvent event = new WeatherEvent();
        event.setId(id);
        event.setCity(city);
        event.setStatus(status);
        event.setTemperature(temperature);
        event.setZonedDateTime(zonedDateTime);
        return event;
    }

    public String getId() {
        return id;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getStatus() {
        return status;
    }

    public String getCity() {
        return city;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "WeatherEvent{" +
                "city='" + city + '\'' +
                ", temperature=" + temperature +
                ", status=" + status +
                '}';
    }
}
