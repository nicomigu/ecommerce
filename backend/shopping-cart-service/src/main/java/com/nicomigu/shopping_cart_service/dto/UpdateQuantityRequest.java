package com.nicomigu.shopping_cart_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

// UpdateQuantityRequest.java
public record UpdateQuantityRequest(
        @Min(value = 1, message = "Quantity must be at least 1") @Max(value = 100, message = "Quantity must not exceed 100") Integer quantity) {
}