package com.example.profitforecast.config;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.example.profitforecast.openApi.OpenApiSpecValidator;
import lombok.SneakyThrows;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import static java.nio.charset.StandardCharsets.UTF_8;

@TestConfiguration
public class OpenApiConfig {
    @Bean
    @SneakyThrows
    public OpenApiSpecValidator openApiSpecValidator() {
        return new OpenApiSpecValidator(OpenApiInteractionValidator
                .createForInlineApiSpecification(new ClassPathResource("open-api-spec.yml").getContentAsString(UTF_8))
                .build());
    }
}
