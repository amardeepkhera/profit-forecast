package com.example.profitforecast.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "app.integration.inventory")
public record InventoryConfig(
        String baseUrl,
        Boolean skipSSLVerification,
        Map<String, String> endpoints
) {
    public String getAllProductsCostUrl() {
        return baseUrl() + endpoints().get("getAllProductsCost");
    }
}
