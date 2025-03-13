package com.example.profitforecast.openApi;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Arrays;

public class BeforeEachMethod implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Arrays.stream(extensionContext.getRequiredTestMethod().getDeclaredAnnotations())
                .filter(annotation -> annotation.annotationType() == ValidateOpenApiSpec.class)
                .findFirst()
                .map(annotation ->
                        Arrays.stream(extensionContext.getRequiredTestClass().getMethods())
                                .filter(method -> method.getName().equals(""))
                                .findFirst()
//                                .map(method -> RefU)
                                .orElseThrow(()->new RuntimeException("no method found"))

                );
    }
}
