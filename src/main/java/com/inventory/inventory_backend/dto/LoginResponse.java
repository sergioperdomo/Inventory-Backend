package com.inventory.inventory_backend.dto;

public record LoginResponse(
        String token,
        String username,
        String role
) {
}
