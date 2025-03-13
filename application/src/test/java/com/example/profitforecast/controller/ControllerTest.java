package com.example.profitforecast.controller;

import com.example.profitforecast.config.OpenApiConfig;
import com.example.profitforecast.config.WebTestClientConfig;
import com.example.profitforecast.extension.WebTestClientParameterResolver;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@ExtendWith(value = {WebTestClientParameterResolver.class})
@Import({WebTestClientConfig.class, OpenApiConfig.class})
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerTest {
}
