package com.nicomigu.shopping_cart_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CartItemRequest(
                @NotNull(message = "Product ID is required") Long productId,

                @Min(value = 1, message = "Quantity must be at least 1") @Max(value = 100, message = "Quantity must not exceed 100") Integer quantity,

                @NotNull(message = "Unit price is required") @DecimalMin(value = "0.01", message = "Price must be greater than 0") BigDecimal unitPrice) {
}
