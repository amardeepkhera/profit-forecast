package com.example.profitforecast.controller;

import org.junit.jupiter.api.Test;


public class NonFunctionalTest extends AbstractControllerTest {

    @Test
    void healthEndpointShouldBeAvailable() {
        webTestClient
                .get()
                .uri("/actuator/health")
                .exchange()
                .expectStatus().isOk()
                .expectHeader()
                .exists("correlation-id");
    }

    @Test
    void errorResponseHeaderShouldIncludeCorrelationId() {
        webTestClient
                .get()
                .uri("/actuator/healthh")
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader()
                .exists("correlation-id");
    }
}
