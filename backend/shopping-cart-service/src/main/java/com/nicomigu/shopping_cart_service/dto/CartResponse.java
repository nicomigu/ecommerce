package com.nicomigu.shopping_cart_service.dto;

import java.util.List;

import com.nicomigu.shopping_cart_service.model.ShoppingCart;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// CartResponse.java
public record CartResponse(
        Long id,
        Long userId,
        List<CartItemResponse> items,
        BigDecimal total,
        LocalDateTime lastModified) {
}