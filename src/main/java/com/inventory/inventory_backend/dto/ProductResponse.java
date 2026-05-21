package com.inventory.inventory_backend.dto;

import com.inventory.inventory_backend.entity.Product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        String sku,
        BigDecimal price,
        Integer stockQuantity,
        Integer minStockAlert,
        boolean lowStock,           // true cuando stock <= minStockAlert
        String categoryName,
        String supplierName
) {
    public static ProductResponse from(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getSku(),
                p.getPrice(),
                p.getStockQuantity(),
                p.getMinStockAlert(),
                p.isLowStock(),
                p.getCategory() != null ? p.getCategory().getName() : null,
                p.getSupplier()  != null ? p.getSupplier().getName()  : null
        );
    }
}
