package com.nicomigu.product_service.dto;

import java.math.BigDecimal;

public record ProductDTO(Long id, String name, String description, BigDecimal price, int stock, String imageUrl) {
}
