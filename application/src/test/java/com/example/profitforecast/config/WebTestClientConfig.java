package com.example.profitforecast.config;

import com.example.profitforecast.openApi.OpenApiSpecValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

@Lazy
@TestConfiguration
public class WebTestClientConfig {

    @LocalServerPort
    private Integer port;

    @Bean
    @Primary
    public WebTestClient webTestClient() {
        return WebTestClient.bindToServer().baseUrl("http://localhost:" + port)
                .build();
    }

    @Bean
    @Qualifier("webTestClientWithOpenApiSpec")
    public WebTestClient webTestClientWithOpenApiSpec(OpenApiSpecValidator openApiSpecValidator) {
        return WebTestClient.bindToServer().baseUrl("http://localhost:" + port)
                .filter(ExchangeFilterFunction.ofRequestProcessor(clientRequest -> Mono.just(clientRequest).doOnNext(openApiSpecValidator::validate)))
                .filter(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> Mono.just(clientResponse).doOnNext(openApiSpecValidator::validate)))
                .build();
    }
}
