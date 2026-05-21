package com.inventory.inventory_backend.controller;

import com.inventory.inventory_backend.dto.StockMovementRequest;
import com.inventory.inventory_backend.dto.StockMovementResponse;
import com.inventory.inventory_backend.service.StockMovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
@Tag(name = "Movimientos de stock", description = "Registrar entradas y salidas de inventario")
public class StockMovementController {
    private final StockMovementService movementService;

    @GetMapping
    @Operation(summary = "Listar todos los movimientos")
    public List<StockMovementResponse> findAll() { return movementService.findAll(); }

    @GetMapping("/by-product/{productId}")
    @Operation(summary = "Historial de movimientos de un producto")
    public List<StockMovementResponse> byProduct(@PathVariable Long productId) {
        return movementService.findByProduct(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar una entrada o salida de stock")
    public StockMovementResponse register(@Valid @RequestBody StockMovementRequest request) {
        return movementService.register(request);
    }
}
