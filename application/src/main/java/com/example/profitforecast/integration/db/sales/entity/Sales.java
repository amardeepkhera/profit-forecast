package com.example.profitforecast.integration.db.sales.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY)
    private Product product;
    private Integer year;
    private Integer month;
    private Long unitsSold;
    private BigDecimal totalDollarSales;
}
