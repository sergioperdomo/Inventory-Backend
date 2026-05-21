package com.inventory.inventory_backend.controller;

import com.inventory.inventory_backend.dto.ProductRequest;
import com.inventory.inventory_backend.dto.ProductResponse;
import com.inventory.inventory_backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "CRUD de productos e inventario")
public class ProductController {


    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Listar todos los productos")
    public List<ProductResponse> findAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por ID")
    public ProductResponse findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Productos con alerta de stock bajo")
    public List<ProductResponse> findLowStock() {
        return productService.findLowStock();
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar productos por nombre")
    public List<ProductResponse> search(@RequestParam String name) {
        return productService.searchByName(name);
    }

    @GetMapping("/by-category/{categoryId}")
    @Operation(summary = "Filtrar productos por categoría")
    public List<ProductResponse> byCategory(@PathVariable Long categoryId) {
        return productService.findByCategory(categoryId);
    }

    @GetMapping("/by-supplier/{supplierId}")
    @Operation(summary = "Filtrar productos por proveedor")
    public List<ProductResponse> bySupplier(@PathVariable Long supplierId) {
        return productService.findBySupplier(supplierId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo producto")
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        return productService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar un producto")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
