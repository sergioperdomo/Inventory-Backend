package com.inventory.inventory_backend.dto;

import com.inventory.inventory_backend.enums.MovementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StockMovementRequest(
        @NotNull(message = "El tipo de movimiento es obligatorio")
        MovementType type,

        @NotNull
        @Min(value = 1, message = "La cantidad mínima es 1")
        Integer quantity,

        @NotNull(message = "El producto es obligatorio")
        Long productId,

        @Size(max = 255)
        String notes
) {}
