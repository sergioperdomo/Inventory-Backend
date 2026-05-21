package com.inventory.inventory_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SupplierRequest(
        @NotBlank @Size(max = 150) String name,
        @Size(max = 100) String contactName,
        @Email @Size(max = 100) String email,
        @Size(max = 20) String phone,
        @Size(max = 255) String address
) {
}
