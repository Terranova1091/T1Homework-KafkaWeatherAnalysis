package com.t1homework.weather.collectionMicroservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherCollectionMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherCollectionMicroservicesApplication.class, args);
	}

}
