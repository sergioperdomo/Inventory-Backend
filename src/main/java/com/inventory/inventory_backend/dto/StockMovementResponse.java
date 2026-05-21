package com.inventory.inventory_backend.dto;

import com.inventory.inventory_backend.entity.StockMovement;
import com.inventory.inventory_backend.enums.MovementType;

import java.time.LocalDateTime;

public record StockMovementResponse(
        Long id,
        MovementType type,
        Integer quantity,
        LocalDateTime createdAt,
        String notes,
        Long productId,
        String productName
) {
    public static StockMovementResponse from(StockMovement m) {
        return new StockMovementResponse(
                m.getId(),
                m.getType(),
                m.getQuantity(),
                m.getCreatedAt(),
                m.getNotes(),
                m.getProduct().getId(),
                m.getProduct().getName()
        );
    }
}
