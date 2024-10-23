package com.nicomigu.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductCreateDTO(
                @NotBlank String name,
                @NotBlank String description,
                @NotNull @Positive BigDecimal price,
                Integer stock,
                String imageUrl) {
}
