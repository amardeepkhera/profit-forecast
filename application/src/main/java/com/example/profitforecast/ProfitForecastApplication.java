package com.example.profitforecast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@ConfigurationPropertiesScan
public class ProfitForecastApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProfitForecastApplication.class, args);
    }
}
