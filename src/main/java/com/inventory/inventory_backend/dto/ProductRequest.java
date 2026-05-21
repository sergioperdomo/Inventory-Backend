package com.inventory.inventory_backend.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 150)
        String name,

        @Size(max = 500)
        String description,

        @Size(max = 50)
        String sku,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal price,

        @NotNull
        @Min(0)
        Integer stockQuantity,

        @NotNull
        @Min(0)
        Integer minStockAlert,

        Long categoryId,
        Long supplierId
) {}
