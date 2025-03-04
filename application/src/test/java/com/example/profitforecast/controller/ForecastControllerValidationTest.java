package com.example.profitforecast.controller;

import com.example.profitforecast.domain.model.ErrorResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ForecastControllerValidationTest extends AbstractControllerTest {

    @ParameterizedTest
    @ValueSource(strings = {"22", "-2", "qwe"})
    void shouldReturnBadRequestForInvalidMonth(String month) {
        var expectedResponse = objectToJson(new ErrorResponse(HttpStatus.BAD_REQUEST, List.of(new ErrorResponse.ErrorDetail("Invalid month or year"))));
        callGetProfitForecastEndpoint(month, "2000")
                .expectStatus().isBadRequest()
                .expectBody()
                .json(expectedResponse);
    }

    @ParameterizedTest
    @ValueSource(strings = {"202w", "qwe"})
    void shouldReturnBadRequestForInvalidYear(String year) {
        var expectedResponse = objectToJson(new ErrorResponse(HttpStatus.BAD_REQUEST, List.of(new ErrorResponse.ErrorDetail("Invalid month or year"))));
        callGetProfitForecastEndpoint("12", year)
                .expectStatus().isBadRequest()
                .expectBody()
                .json(expectedResponse);
    }
}
