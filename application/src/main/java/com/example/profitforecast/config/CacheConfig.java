package com.example.profitforecast.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CacheConfig {
    public static final String GET_ALL_PRODUCTS_COST_NAME = "getAllProductsCost";

    @Bean
    public CaffeineCache getAllProductsCostCache(@Value("${app.cache.getAllProductsCost.expiry}") Duration expiry) {
        return new CaffeineCache(GET_ALL_PRODUCTS_COST_NAME,
                Caffeine.newBuilder()
                        .expireAfterWrite(expiry)
                        .recordStats()
                        .build()
        );
    }
}
