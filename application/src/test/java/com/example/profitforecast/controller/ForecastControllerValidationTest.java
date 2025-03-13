package com.example.profitforecast.controller;

import com.example.profitforecast.domain.model.ErrorResponse;
import org.assertj.core.description.Description;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatRuntimeException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test()
    void shouldFailWhenValidatingOpenApiSpec(WebTestClient webTestClientWithOpenApiSpec) {
        assertThatThrownBy(() -> callGetProfitForecastEndpoint("2", "2000", webTestClientWithOpenApiSpec))
                .hasMessage("No API path found that matches request '/forecast/brands'.");
    }
}
