package com.inventory.inventory_backend.dto;

import com.inventory.inventory_backend.entity.Category;

public record CategoryResponse(
        Long id,
        String name,
        String description,
        int productCount
) {
    public static CategoryResponse from(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getProducts().size()
        );
    }
}
