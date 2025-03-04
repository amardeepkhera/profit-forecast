package com.example.profitforecast.controller;

import com.example.profitforecast.domain.model.ProfitForecastPerBrand;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;

public class ForecastControllerTest extends AbstractControllerTest {

    @Test
    void shouldReturnProfitForecastFor1ProductOf1BrandFor1Month() {
        var appleIphone15 = testRepository.getAppleIphone15();
        var sale = createSale(appleIphone15.entity(), Month.JANUARY, Year.of(2000), 1L, appleIphone15.costPrice());
        var testData = Map.of(appleIphone15, List.of(sale));

        var expectedResponse = objectToJson(List.of(
                new ProfitForecastPerBrand(appleIphone15.entity().getBrand(), calculateForecastQty(testData).intValue(), calculateForecastProfit(testData).intValue()))
        );

        callGetProfitForecastEndpoint("2", "2000")
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }

    @Test
    void shouldReturnProfitForecastFor1ProductOf1BrandFor3Months() {
        var appleIphone15 = testRepository.getAppleIphone15();
        var sale1 = createSale(appleIphone15.entity(), Month.JANUARY, Year.of(2000), 1L, appleIphone15.costPrice());
        var sale2 = createSale(appleIphone15.entity(), Month.FEBRUARY, Year.of(2000), 20L, new BigDecimal("10000"));
        var sale3 = createSale(appleIphone15.entity(), Month.MARCH, Year.of(2000), 100L, new BigDecimal("500000"));
        var testData = Map.of(appleIphone15, List.of(sale1, sale2, sale3));

        var expectedResponse = objectToJson(List.of(
                new ProfitForecastPerBrand(appleIphone15.entity().getBrand(), calculateForecastQty(testData).intValue(), calculateForecastProfit(testData).intValue()))
        );

        callGetProfitForecastEndpoint("4", "2000")
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }

    @Test
    void shouldReturnProfitForecastFor2ProductsOf1BrandFor1Month() {
        var appleIphone15 = testRepository.getAppleIphone15();
        var appleIphone15Pro = testRepository.getAppleIphone15Pro();
        var sale1 = createSale(appleIphone15.entity(), Month.JANUARY, Year.of(2000), 1L, appleIphone15.costPrice());
        var sale2 = createSale(appleIphone15Pro.entity(), Month.JANUARY, Year.of(2000), 1L, appleIphone15Pro.costPrice());

        var testData = Map.of(
                appleIphone15, List.of(sale1),
                appleIphone15Pro, List.of(sale2)
        );
        var expectedResponse = objectToJson(List.of(
                new ProfitForecastPerBrand(appleIphone15.entity().getBrand(), calculateForecastQty(testData).intValue(), calculateForecastProfit(testData).intValue()))
        );
        callGetProfitForecastEndpoint("2", "2000")
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }

    @Test
    void shouldReturnProfitForecastFor2ProductsOf1BrandFor3Months() {
        var appleIphone15 = testRepository.getAppleIphone15();
        var appleIphone15Pro = testRepository.getAppleIphone15Pro();
        var sale1 = createSale(appleIphone15.entity(), Month.JANUARY, Year.of(2000), 1L, appleIphone15.costPrice());
        var sale2 = createSale(appleIphone15.entity(), Month.FEBRUARY, Year.of(2000), 30098L, new BigDecimal("23909009"));
        var sale3 = createSale(appleIphone15.entity(), Month.MARCH, Year.of(2000), 98675L, new BigDecimal("876576890"));

        var sale4 = createSale(appleIphone15Pro.entity(), Month.JANUARY, Year.of(2000), 1L, appleIphone15Pro.costPrice());
        var sale5 = createSale(appleIphone15Pro.entity(), Month.FEBRUARY, Year.of(2000), 197678L, new BigDecimal("876567867"));
        var sale6 = createSale(appleIphone15Pro.entity(), Month.MARCH, Year.of(2000), 12343L, new BigDecimal("176567867"));

        var testData = Map.of(
                appleIphone15, List.of(sale1, sale2, sale3),
                appleIphone15Pro, List.of(sale4, sale5, sale6)
        );
        var expectedResponse = objectToJson(List.of(
                new ProfitForecastPerBrand(appleIphone15.entity().getBrand(), calculateForecastQty(testData).intValue(), calculateForecastProfit(testData).intValue()))
        );
        callGetProfitForecastEndpoint("4", "2000")
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }

    @Test
    void shouldReturnProfitForecastFor2ProductsOf2BrandFor1Month() {
        var appleIphone15 = testRepository.getAppleIphone15();
        var googlePixel7 = testRepository.getGooglePixel7();
        var sale1 = createSale(appleIphone15.entity(), Month.JANUARY, Year.of(2000), 1L, appleIphone15.costPrice());
        var sale2 = createSale(googlePixel7.entity(), Month.JANUARY, Year.of(2000), 1L, googlePixel7.costPrice());

        var appleTestData = Map.of(appleIphone15, List.of(sale1));
        var googleTestData = Map.of(googlePixel7, List.of(sale2));

        var expectedResponse = objectToJson(List.of(
                new ProfitForecastPerBrand(appleIphone15.entity().getBrand(), calculateForecastQty(appleTestData).intValue(), calculateForecastProfit(appleTestData).intValue()),
                new ProfitForecastPerBrand(googlePixel7.entity().getBrand(), calculateForecastQty(googleTestData).intValue(), calculateForecastProfit(googleTestData).intValue())
        ));
        callGetProfitForecastEndpoint("2", "2000")
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }

    @Test
    void shouldReturnProfitForecastFor2ProductsOf2BrandFor3Months() {
        var appleIphone15 = testRepository.getAppleIphone15();
        var googlePixel7 = testRepository.getGooglePixel7();
        var sale1 = createSale(appleIphone15.entity(), Month.AUGUST, Year.of(2000), 1L, appleIphone15.costPrice());
        var sale2 = createSale(appleIphone15.entity(), Month.SEPTEMBER, Year.of(2000), 1390L, new BigDecimal("345540"));
        var sale3 = createSale(appleIphone15.entity(), Month.OCTOBER, Year.of(2000), 12222L, new BigDecimal("8766449"));

        var sale4 = createSale(googlePixel7.entity(), Month.AUGUST, Year.of(2000), 1L, googlePixel7.costPrice());
        var sale5 = createSale(googlePixel7.entity(), Month.SEPTEMBER, Year.of(2000), 43322L, new BigDecimal("2344899"));
        var sale6 = createSale(googlePixel7.entity(), Month.OCTOBER, Year.of(2000), 75455L, new BigDecimal("9087655"));

        var appleTestData = Map.of(appleIphone15, List.of(sale1, sale2, sale3));
        var googleTestData = Map.of(googlePixel7, List.of(sale4, sale5, sale6));

        var expectedResponse = objectToJson(List.of(
                new ProfitForecastPerBrand(appleIphone15.entity().getBrand(), calculateForecastQty(appleTestData).intValue(), calculateForecastProfit(appleTestData).intValue()),
                new ProfitForecastPerBrand(googlePixel7.entity().getBrand(), calculateForecastQty(googleTestData).intValue(), calculateForecastProfit(googleTestData).intValue())
        ));
        callGetProfitForecastEndpoint("11", "2000")
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }

    @Test
    void shouldReturnProfitForecastFor4ProductsOf3BrandFor3Months() {
        var appleIphone15 = testRepository.getAppleIphone15();
        var appleIphone15Pro = testRepository.getAppleIphone15Pro();
        var googlePixel7 = testRepository.getGooglePixel7();
        var samsungGalaxyFlip = testRepository.getSamsungGalaxyFlip();

        var sale1 = createSale(appleIphone15.entity(), Month.AUGUST, Year.of(2000), 1L, appleIphone15.costPrice());
        var sale2 = createSale(appleIphone15.entity(), Month.SEPTEMBER, Year.of(2000), 139L, new BigDecimal("3457"));
        var sale3 = createSale(appleIphone15.entity(), Month.OCTOBER, Year.of(2000), 122L, new BigDecimal("87664"));

        var sale4 = createSale(appleIphone15Pro.entity(), Month.AUGUST, Year.of(2000), 1L, appleIphone15Pro.costPrice());
        var sale5 = createSale(appleIphone15Pro.entity(), Month.SEPTEMBER, Year.of(2000), 129L, new BigDecimal("345799"));
        var sale6 = createSale(appleIphone15Pro.entity(), Month.OCTOBER, Year.of(2000), 1221L, new BigDecimal("8762277"));

        var sale7 = createSale(googlePixel7.entity(), Month.AUGUST, Year.of(2000), 1L, googlePixel7.costPrice());
        var sale8 = createSale(googlePixel7.entity(), Month.SEPTEMBER, Year.of(2000), 433L, new BigDecimal("2348000"));
        var sale9 = createSale(googlePixel7.entity(), Month.OCTOBER, Year.of(2000), 754L, new BigDecimal("28765555"));

        var sale10 = createSale(samsungGalaxyFlip.entity(), Month.AUGUST, Year.of(2000), 1L, samsungGalaxyFlip.costPrice());
        var sale11 = createSale(samsungGalaxyFlip.entity(), Month.SEPTEMBER, Year.of(2000), 493L, new BigDecimal("932266"));
        var sale12 = createSale(samsungGalaxyFlip.entity(), Month.OCTOBER, Year.of(2000), 234L, new BigDecimal("340099"));

        var appleTestData = Map.of(
                appleIphone15, List.of(sale1, sale2, sale3),
                appleIphone15Pro, List.of(sale4, sale5, sale6)
        );
        var googleTestData = Map.of(googlePixel7, List.of(sale7, sale8, sale9));
        var samsungTestData = Map.of(samsungGalaxyFlip, List.of(sale10, sale11, sale12));

        var expectedResponse = objectToJson(List.of(
                new ProfitForecastPerBrand(appleIphone15.entity().getBrand(), calculateForecastQty(appleTestData).intValue(), calculateForecastProfit(appleTestData).intValue()),
                new ProfitForecastPerBrand(googlePixel7.entity().getBrand(), calculateForecastQty(googleTestData).intValue(), calculateForecastProfit(googleTestData).intValue()),
                new ProfitForecastPerBrand(samsungGalaxyFlip.entity().getBrand(), calculateForecastQty(samsungTestData).intValue(), calculateForecastProfit(samsungTestData).intValue())
        ));
        callGetProfitForecastEndpoint("11", "2000")
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }

    @Test
    void shouldCalculateForecastForAvailableMonths() {
        var appleIphone15 = testRepository.getAppleIphone15();
        var janSale = createSale(appleIphone15.entity(), Month.JANUARY, Year.of(2000), 1L, appleIphone15.costPrice());
        var marchSale = createSale(appleIphone15.entity(), Month.MARCH, Year.of(2000), 2L, appleIphone15.costPrice().multiply(BigDecimal.TWO));
        var testData = Map.of(appleIphone15, List.of(janSale, marchSale));

        var expectedResponse = objectToJson(List.of(
                new ProfitForecastPerBrand(appleIphone15.entity().getBrand(), calculateForecastQty(testData).intValue(), calculateForecastProfit(testData).intValue()))
        );

        callGetProfitForecastEndpoint("4", "2000")
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }

    @Test
    void shouldExcludeBrandFromResponseIfSalesDataIsNotAvailable() {
        var appleIphone15 = testRepository.getAppleIphone15();
        var googlePixel7 = testRepository.getGooglePixel7();

        createSale(appleIphone15.entity(), Month.JANUARY, Year.of(2000), 1L, appleIphone15.costPrice());
        createSale(appleIphone15.entity(), Month.MARCH, Year.of(2000), 2L, appleIphone15.costPrice().multiply(BigDecimal.TWO));

        var sale3 = createSale(googlePixel7.entity(), Month.JULY, Year.of(2000), 1L, googlePixel7.costPrice());
        var sale4 = createSale(googlePixel7.entity(), Month.AUGUST, Year.of(2000), 4L, googlePixel7.costPrice().multiply(new BigDecimal(6)));

        var googleTestData = Map.of(googlePixel7, List.of(sale3, sale4));

        var expectedResponse = objectToJson(List.of(
                new ProfitForecastPerBrand(googlePixel7.entity().getBrand(), calculateForecastQty(googleTestData).intValue(), calculateForecastProfit(googleTestData).intValue()))
        );


        callGetProfitForecastEndpoint("9", "2000")
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }

    @Test
    void shouldSkipProductFromForecastIfProductCostIsNotAvailable() {
        var appleIphone15 = testRepository.getAppleIphone15();
        var newAppleProduct = createNewAppleProduct();

        var sale1 = createSale(appleIphone15.entity(), Month.JANUARY, Year.of(2000), 1L, appleIphone15.costPrice());

        createSale(newAppleProduct.entity(), Month.JANUARY, Year.of(2000), 1L, BigDecimal.ZERO);

        var testData = Map.of(appleIphone15, List.of(sale1));

        var expectedResponse = objectToJson(List.of(
                new ProfitForecastPerBrand(appleIphone15.entity().getBrand(), calculateForecastQty(testData).intValue(), calculateForecastProfit(testData).intValue()))
        );

        callGetProfitForecastEndpoint("2", "2000")
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }

    @Test
    void shouldSkipProductFromForecastIfProductSaleIsNotPresent() {
        var appleIphone15 = testRepository.getAppleIphone15();
        createNewAppleProduct();

        var sale = createSale(appleIphone15.entity(), Month.JANUARY, Year.of(2000), 1L, appleIphone15.costPrice());
        var testData = Map.of(appleIphone15, List.of(sale));

        var expectedResponse = objectToJson(List.of(
                new ProfitForecastPerBrand(appleIphone15.entity().getBrand(), calculateForecastQty(testData).intValue(), calculateForecastProfit(testData).intValue()))
        );
        callGetProfitForecastEndpoint("2", "2000")
                .expectStatus().isOk()
                .expectBody()
                .json(expectedResponse);
    }
}
