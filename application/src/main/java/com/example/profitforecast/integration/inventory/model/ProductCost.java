package com.example.profitforecast.integration.inventory.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ProductCost(@JsonProperty("product") Long productId, BigDecimal costPrice) {
}
