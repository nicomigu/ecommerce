package com.nicomigu.shopping_cart_service.exception;

import java.util.List;

// ErrorResponse.java
public record ErrorResponse(
        String code,
        String message,
        List<String> details) {
    public ErrorResponse(String code, String message) {
        this(code, message, List.of());
    }
}