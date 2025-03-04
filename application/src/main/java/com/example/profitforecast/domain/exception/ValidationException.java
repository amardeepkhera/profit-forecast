package com.example.profitforecast.domain.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final String message;

    public ValidationException(String message) {
        super(message);
        this.message = message;
    }
}
