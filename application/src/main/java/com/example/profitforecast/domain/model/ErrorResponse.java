package com.example.profitforecast.domain.model;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResponse(HttpStatus status, List<ErrorDetail> errors) {
    public record ErrorDetail(String message){}
}
