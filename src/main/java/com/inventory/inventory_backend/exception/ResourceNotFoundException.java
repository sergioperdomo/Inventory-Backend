package com.inventory.inventory_backend.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " con id " + id + " no encontrado");
    }
}
