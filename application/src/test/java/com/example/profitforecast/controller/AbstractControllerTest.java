package com.example.profitforecast.controller;

import com.example.profitforecast.integration.db.sales.TestRepository;
import com.example.profitforecast.integration.db.sales.TestRepository.TestProduct;
import com.example.profitforecast.integration.db.sales.entity.Product;
import com.example.profitforecast.integration.db.sales.entity.Sales;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public abstract class AbstractControllerTest {

    @Autowired
    protected WebTestClient webTestClient;
    @Autowired
    protected TestRepository testRepository;
    @Autowired
    private ObjectMapper objectMapper;


    protected final List<Sales> testSales = new ArrayList<>();
    protected final List<TestProduct> testProducts = new ArrayList<>();

    @AfterEach
    protected void cleanTestData() {
        testRepository.deleteSale(testSales);
        testSales.clear();
        testRepository.deleteProducts(testProducts);
        testProducts.clear();
    }

    protected WebTestClient.ResponseSpec callGetProfitForecastEndpoint(String month, String year) {
        return webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/forecast/brands")
                        .queryParam("month", month)
                        .queryParam("year", year)
                        .build()
                )
                .exchange();
    }

    protected TestProduct createNewAppleProduct() {
        var product = testRepository.createProduct(testRepository.getAppleIphone15().entity().getBrand());
        testProducts.add(product);
        return product;
    }

    protected Sales createSale(Product product, Month month, Year year, Long unitsSold, BigDecimal totalDollarSales) {
        var sale = testRepository.createSale(product, month, year, unitsSold, totalDollarSales);
        testSales.add(sale);
        return sale;
    }

    protected BigDecimal calculateForecastQty(List<Sales> salesPerProduct) {
        return salesPerProduct.stream()
                .map(Sales::getUnitsSold)
                .reduce(Long::sum)
                .map(sum -> new BigDecimal(sum).divide(new BigDecimal(salesPerProduct.size()), RoundingMode.FLOOR))
                .get();
    }

    protected BigDecimal calculateForecastQty(Map<TestProduct, List<Sales>> salesPerBrand) {
        var qtyForecast = new AtomicReference<>(BigDecimal.ZERO);
        salesPerBrand.values()
                .forEach(salesList -> {
                    var qtyAvg = salesList.stream()
                            .map(Sales::getUnitsSold)
                            .reduce(Long::sum)
                            .map(sum -> new BigDecimal(sum).divide(new BigDecimal(salesList.size()), RoundingMode.FLOOR))
                            .get();
                    qtyForecast.set(qtyAvg.add(qtyForecast.get()));
                });
        return qtyForecast.get();
    }

    protected BigDecimal calculateForecastProfit(Map<TestProduct, List<Sales>> salesPerBrand) {
        var profitForecast = new AtomicReference<>(BigDecimal.ZERO);
        salesPerBrand.forEach((testProduct, salesList) -> {
            var salesAvg = salesList.stream()
                    .map(Sales::getTotalDollarSales)
                    .reduce(BigDecimal::add)
                    .map(sum -> sum.divide(new BigDecimal(salesList.size()), RoundingMode.FLOOR))
                    .get();
            profitForecast.set(profitForecast.get().add(salesAvg.subtract(calculateForecastQty(salesList).multiply(testProduct.costPrice()))));
        });
        return profitForecast.get();

    }

    @SneakyThrows
    protected String objectToJson(Object o) {
        return objectMapper.writeValueAsString(o);
    }
}
