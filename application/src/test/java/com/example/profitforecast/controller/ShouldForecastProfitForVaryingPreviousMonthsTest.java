package com.example.profitforecast.controller;

import com.example.profitforecast.config.properties.AppConfig;
import com.example.profitforecast.domain.model.ProfitForecastPerBrand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;

public class ShouldForecastProfitForVaryingPreviousMonthsTest extends AbstractControllerTest {

    @MockBean
    private AppConfig appConfig;

    @Test
    void shouldReturnProfitForecastFor1ProductOf1BrandFor4Months() {
        Mockito.when(appConfig.previousNMonthsSales()).thenReturn(4);
        var appleIphone15 = testRepository.getAppleIphone15();
        var sale1 = createSale(appleIphone15.entity(), Month.SEPTEMBER, Year.of(1999), 1L, appleIphone15.costPrice());
        var sale2 = createSale(appleIphone15.entity(), Month.DECEMBER, Year.of(1999), 2L, appleIphone15.costPrice().multiply(new BigDecimal(5)));
        var sale3 = createSale(appleIphone15.entity(), Month.NOVEMBER, Year.of(1999), 3L, appleIphone15.costPrice().multiply(new BigDecimal(6)));
        var sale4 = createSale(appleIphone15.entity(), Month.OCTOBER, Year.of(1999), 10L, appleIphone15.costPrice().multiply(new BigDecimal(8)));
        var testData = Map.of(appleIphone15, List.of(sale1, sale2, sale3, sale4));

        createSale(appleIphone15.entity(), Month.JANUARY, Year.of(2000), 10L, appleIphone15.costPrice().multiply(new BigDecimal(8)));

        var expectedResponse = objectToJson(List.of(
                new ProfitForecastPerBrand(appleIphone15.entity().getBrand(), calculateForecastQty(testData).intValue(), calculateForecastProfit(testData).intValue()))
        );
        callGetProfitForecastEndpoint("1", "2000")
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }

    @Test
    void shouldReturnProfitForecastFor1ProductOf1BrandFor12Months() {
        Mockito.when(appConfig.previousNMonthsSales()).thenReturn(12);
        var appleIphone15 = testRepository.getAppleIphone15();
        var sale1 = createSale(appleIphone15.entity(), Month.SEPTEMBER, Year.of(1999), 1L, appleIphone15.costPrice());
        var sale2 = createSale(appleIphone15.entity(), Month.DECEMBER, Year.of(1999), 2L, appleIphone15.costPrice().multiply(new BigDecimal(5)));
        var sale3 = createSale(appleIphone15.entity(), Month.NOVEMBER, Year.of(1999), 3L, appleIphone15.costPrice().multiply(new BigDecimal(6)));
        var sale4 = createSale(appleIphone15.entity(), Month.OCTOBER, Year.of(1999), 10L, appleIphone15.costPrice().multiply(new BigDecimal(8)));
        var testData = Map.of(appleIphone15, List.of(sale1, sale2, sale3, sale4));

        var expectedResponse = objectToJson(List.of(
                new ProfitForecastPerBrand(appleIphone15.entity().getBrand(), calculateForecastQty(testData).intValue(), calculateForecastProfit(testData).intValue()))
        );
        callGetProfitForecastEndpoint("1", "2000")
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }

    @Test
    void shouldReturnProfitForecastFor1ProductOf1BrandFor13Months() {
        Mockito.when(appConfig.previousNMonthsSales()).thenReturn(13);
        var appleIphone15 = testRepository.getAppleIphone15();
        var sale1 = createSale(appleIphone15.entity(), Month.SEPTEMBER, Year.of(1999), 1L, appleIphone15.costPrice());
        var sale2 = createSale(appleIphone15.entity(), Month.DECEMBER, Year.of(1999), 2L, appleIphone15.costPrice().multiply(new BigDecimal(5)));
        var sale3 = createSale(appleIphone15.entity(), Month.NOVEMBER, Year.of(1999), 3L, appleIphone15.costPrice().multiply(new BigDecimal(6)));
        var sale4 = createSale(appleIphone15.entity(), Month.OCTOBER, Year.of(1999), 10L, appleIphone15.costPrice().multiply(new BigDecimal(8)));
        var sale5 = createSale(appleIphone15.entity(), Month.JULY, Year.of(1999), 10L, appleIphone15.costPrice().multiply(new BigDecimal(8)));
        var sale6 = createSale(appleIphone15.entity(), Month.DECEMBER, Year.of(1998), 3L, appleIphone15.costPrice().multiply(new BigDecimal(2)));
        var testData = Map.of(appleIphone15, List.of(sale1, sale2, sale3, sale4, sale5, sale6));

        var expectedResponse = objectToJson(List.of(
                new ProfitForecastPerBrand(appleIphone15.entity().getBrand(), calculateForecastQty(testData).intValue(), calculateForecastProfit(testData).intValue()))
        );
        callGetProfitForecastEndpoint("1", "2000")
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }

    @Test
    void shouldReturnProfitForecastFor1ProductOf1BrandFor45Months() {
        Mockito.when(appConfig.previousNMonthsSales()).thenReturn(45);
        var appleIphone15 = testRepository.getAppleIphone15();
        var sale1 = createSale(appleIphone15.entity(), Month.SEPTEMBER, Year.of(1999), 1L, appleIphone15.costPrice());
        var sale2 = createSale(appleIphone15.entity(), Month.DECEMBER, Year.of(1999), 2L, appleIphone15.costPrice().multiply(new BigDecimal(5)));
        var sale3 = createSale(appleIphone15.entity(), Month.NOVEMBER, Year.of(1999), 3L, appleIphone15.costPrice().multiply(new BigDecimal(6)));
        var sale4 = createSale(appleIphone15.entity(), Month.OCTOBER, Year.of(1999), 10L, appleIphone15.costPrice().multiply(new BigDecimal(8)));
        var sale5 = createSale(appleIphone15.entity(), Month.JULY, Year.of(1999), 10L, appleIphone15.costPrice().multiply(new BigDecimal(8)));
        var sale6 = createSale(appleIphone15.entity(), Month.DECEMBER, Year.of(1998), 3L, appleIphone15.costPrice().multiply(new BigDecimal(2)));
        var sale7 = createSale(appleIphone15.entity(), Month.JULY, Year.of(1997), 3L, appleIphone15.costPrice().multiply(new BigDecimal(2)));
        var testData = Map.of(appleIphone15, List.of(sale1, sale2, sale3, sale4, sale5, sale6, sale7));

        createSale(appleIphone15.entity(), Month.MARCH, Year.of(2000), 3L, appleIphone15.costPrice().multiply(new BigDecimal(2)));

        var expectedResponse = objectToJson(List.of(
                new ProfitForecastPerBrand(appleIphone15.entity().getBrand(), calculateForecastQty(testData).intValue(), calculateForecastProfit(testData).intValue()))
        );
        callGetProfitForecastEndpoint("3", "2000")
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }
}
