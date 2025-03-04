package com.example.profitforecast.service;

import com.example.profitforecast.config.properties.AppConfig;
import com.example.profitforecast.domain.MonthOfAYear;
import com.example.profitforecast.domain.model.ProfitForecastPerBrand;
import com.example.profitforecast.integration.db.sales.SalesRepository;
import com.example.profitforecast.integration.db.sales.entity.ProductSales;
import com.example.profitforecast.integration.inventory.CachedProductCostRepository;
import com.example.profitforecast.integration.inventory.model.ProductCost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForecastService {
    private final AppConfig appConfig;
    private final CachedProductCostRepository productCostRepository;
    private final SalesRepository salesRepository;

    @Transactional
    public List<ProfitForecastPerBrand> getProfitForecastForAllBrands(MonthOfAYear monthOfAYear) {
        var productSales = salesRepository.getAllProductsSales(monthOfAYear, appConfig.previousNMonthsSales());

        if (productSales.isEmpty()) {
            log.warn("No sales found for any product");
            return Collections.emptyList();
        }
        var productsCost = productCostRepository.getAllProductsCost();
        if (productsCost.isEmpty()) {
            log.warn("Cost not found for any product");
            return Collections.emptyList();
        }

        return getProfitForecastForAllBrands(productSales, productsCost);
    }

    private List<ProfitForecastPerBrand> getProfitForecastForAllBrands(List<ProductSales> productSales, Map<Long, ProductCost> productsCost) {
        return productSales
                .stream()
                .map(ps -> getProfitForecastPerProduct(ps, productsCost.get(ps.getProductId())))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.collectingAndThen(Collectors.groupingBy(ProfitForecastPerProduct::brand), groupedProfitForecastPerProduct ->
                        groupedProfitForecastPerProduct.values()
                                .stream()
                                .map(this::getProfitForecastPerBrand)
                                .collect(Collectors.toList())
                ));
    }

    private Optional<ProfitForecastPerProduct> getProfitForecastPerProduct(ProductSales productSales, ProductCost productCost) {
        return Optional.ofNullable(productCost)
                .map(ProductCost::costPrice)
                .map(cost -> calculateForecastProfitAmountPerProduct(productSales, cost))
                .map(productProfitForecast -> new ProfitForecastPerProduct(
                        productSales.getBrand(),
                        productSales.getAvgUnitsSold(),
                        productProfitForecast)
                );

    }

    private BigDecimal calculateForecastProfitAmountPerProduct(ProductSales productSales, BigDecimal productCost) {
        return productSales.getAvgTotalSales()
                .subtract(productCost.multiply(new BigDecimal(productSales.getAvgUnitsSold())));
    }

    private ProfitForecastPerBrand getProfitForecastPerBrand(List<ProfitForecastPerProduct> profitForecastPerProducts) {
        BigDecimal profitForecastSum = BigDecimal.ZERO;
        int qtyForecastSum = 0;

        for (ProfitForecastPerProduct profitForecastPerProduct : profitForecastPerProducts) {
            profitForecastSum = profitForecastSum.add(profitForecastPerProduct.profitForecast());
            qtyForecastSum = qtyForecastSum + profitForecastPerProduct.avgUnitsSold();
        }
        return new ProfitForecastPerBrand(profitForecastPerProducts.get(0).brand(), qtyForecastSum, profitForecastSum.intValue());
    }

    private record ProfitForecastPerProduct(String brand, Integer avgUnitsSold, BigDecimal profitForecast) {
    }
}
