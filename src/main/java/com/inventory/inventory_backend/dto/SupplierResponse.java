package com.inventory.inventory_backend.dto;

import com.inventory.inventory_backend.entity.Supplier;

public record SupplierResponse(
        Long id,
        String name,
        String contactName,
        String email,
        String phone,
        String address,
        int productCount
) {
    public static SupplierResponse from(Supplier s) {
        return new SupplierResponse(
                s.getId(),
                s.getName(),
                s.getContactName(),
                s.getEmail(),
                s.getPhone(),
                s.getAddress(),
                s.getProducts().size()
        );
    }
}
