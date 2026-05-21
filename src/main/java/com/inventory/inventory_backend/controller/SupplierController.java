package com.inventory.inventory_backend.controller;

import com.inventory.inventory_backend.dto.SupplierRequest;
import com.inventory.inventory_backend.dto.SupplierResponse;
import com.inventory.inventory_backend.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@Tag(name = "Proveedores", description = "Gestión de proveedores")
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping
    @Operation(summary = "Listar todos los proveedores")
    public List<SupplierResponse> findAll() { return supplierService.findAll(); }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener proveedor por ID")
    public SupplierResponse findById(@PathVariable Long id) { return supplierService.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un proveedor")
    public SupplierResponse create(@Valid @RequestBody SupplierRequest request) {
        return supplierService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un proveedor")
    public SupplierResponse update(@PathVariable Long id, @Valid @RequestBody SupplierRequest request) {
        return supplierService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar un proveedor")
    public void delete(@PathVariable Long id) { supplierService.delete(id); }
}
