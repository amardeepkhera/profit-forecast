package com.example.profitforecast.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppConfig(

        Integer previousNMonthsSales
) {
}
