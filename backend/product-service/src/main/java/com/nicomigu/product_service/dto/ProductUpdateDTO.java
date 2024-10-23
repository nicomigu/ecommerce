package com.nicomigu.product_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record ProductUpdateDTO(String name,
        String description,
        @Positive BigDecimal price,
        @Min(0) Integer stock,
        String imageUrl) {

}
