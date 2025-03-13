package com.example.profitforecast.openApi;

import com.atlassian.oai.validator.OpenApiInteractionValidator;
import com.atlassian.oai.validator.model.Request;
import com.atlassian.oai.validator.model.SimpleRequest;
import com.atlassian.oai.validator.model.SimpleResponse;
import com.atlassian.oai.validator.report.ValidationReport;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;

import java.util.Objects;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Getter
public class OpenApiSpecValidator {

    private final OpenApiInteractionValidator openApiInteractionValidator;

    @SneakyThrows
    public OpenApiSpecValidator(ClassPathResource specLocation) {
        Objects.requireNonNull(specLocation, "specLocation cannot be null");
        this.openApiInteractionValidator = OpenApiInteractionValidator.createForInlineApiSpecification(specLocation.getContentAsString(UTF_8)).build();
    }

    @SneakyThrows
    public OpenApiSpecValidator(OpenApiInteractionValidator openApiInteractionValidator) {
        this.openApiInteractionValidator = openApiInteractionValidator;
    }

    public void validate(ClientRequest request) {
        var simpleRequest = new SimpleRequest.Builder(request.method().name(), request.url().getPath())
                .build();
        var report = openApiInteractionValidator.validateRequest(simpleRequest);
        if (report.hasErrors()) {
            throw new RuntimeException(report.getMessages().stream().map(ValidationReport.Message::getMessage).collect(Collectors.joining(",")));
        }
    }

    public void validate(ClientResponse response) {
        var request = response.request();
        var simpleResponse = new SimpleResponse.Builder(response.statusCode().value()).build();
        openApiInteractionValidator.validateResponse(request.getURI().getPath(), Request.Method.valueOf(request.getMethod().name()), simpleResponse);
    }
}

