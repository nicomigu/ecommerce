package com.nicomigu.shopping_cart_service.dto;

import java.math.BigDecimal;

public record CartItemResponse(
        Long id,
        Long productId,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal total) {
}