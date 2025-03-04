package com.example.profitforecast.integration.db.sales.entity;

import java.math.BigDecimal;

public interface ProductSales {

    Long getProductId();

    String getBrand();

    Integer getAvgUnitsSold();

    BigDecimal getAvgTotalSales();
}
