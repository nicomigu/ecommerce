package com.nicomigu.product_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private LocalDateTime timestamp;
    private String details; // Optional: additional information about the error
}
