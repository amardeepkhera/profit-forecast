package com.example.profitforecast.config;

import com.example.profitforecast.config.properties.InventoryConfig;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient inventoryClient(WebClient.Builder builder, InventoryConfig inventoryConfig) {
        return builder
                .clientConnector(new ReactorClientHttpConnector(httpClient(inventoryConfig.skipSSLVerification())))
                .filters(exchangeFilterFunctions -> {
                            exchangeFilterFunctions.add(requestLoggingFilter());
                            exchangeFilterFunctions.add(responseLoggingFilter());
                        }
                ).build();
    }

    private HttpClient httpClient(boolean skipSSLVerification) {
        return HttpClient.create()
                .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext(skipSSLVerification)));
    }

    @SneakyThrows
    private SslContext sslContext(boolean skipSSLVerification) {
        if (skipSSLVerification) {
            return SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        }
        return SslContextBuilder.forClient().build();
    }

    private ExchangeFilterFunction requestLoggingFilter() {
        return ExchangeFilterFunction.ofRequestProcessor(this::logRequest);
    }

    private ExchangeFilterFunction responseLoggingFilter() {
        return ExchangeFilterFunction.ofResponseProcessor(this::logResponse);
    }

    private Mono<ClientRequest> logRequest(ClientRequest clientRequest) {
        return Mono.just(clientRequest)
                .doOnNext(request -> log.info("Calling downstream service {}", request.method() + ":" + request.url()));
    }

    private Mono<ClientResponse> logResponse(ClientResponse clientResponse) {
        return Mono.just(clientResponse)
                .doOnNext(response -> log.info("Received response status {} from downstream service {}", response.statusCode().value(), response.request().getMethod() + ":" + response.request().getURI()));
    }
}
