package com.inventory.inventory_backend.service;
import com.inventory.inventory_backend.dto.StockMovementRequest;
import com.inventory.inventory_backend.dto.StockMovementResponse;
import com.inventory.inventory_backend.entity.Product;
import com.inventory.inventory_backend.entity.StockMovement;
import com.inventory.inventory_backend.enums.MovementType;
import com.inventory.inventory_backend.exception.BusinessException;
import com.inventory.inventory_backend.exception.ResourceNotFoundException;
import com.inventory.inventory_backend.repository.ProductRepository;
import com.inventory.inventory_backend.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockMovementService {
    private final StockMovementRepository movementRepository;
    private final ProductRepository productRepository;

    public List<StockMovementResponse> findByProduct(Long productId) {
        return movementRepository.findByProductIdOrderByCreatedAtDesc(productId)
                .stream()
                .map(StockMovementResponse::from)
                .toList();
    }

    public List<StockMovementResponse> findAll() {
        return movementRepository.findAll()
                .stream()
                .map(StockMovementResponse::from)
                .toList();
    }

    @Transactional
    public StockMovementResponse register(StockMovementRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto", request.productId()));

        // Regla de negocio: no se puede hacer una SALIDA si no hay stock suficiente
        if (request.type() == MovementType.SALIDA) {
            if (product.getStockQuantity() < request.quantity()) {
                throw new BusinessException(
                        "Stock insuficiente. Disponible: " + product.getStockQuantity()
                                + ", solicitado: " + request.quantity()
                );
            }
            product.setStockQuantity(product.getStockQuantity() - request.quantity());
        } else {
            // ENTRADA: sumamos al stock
            product.setStockQuantity(product.getStockQuantity() + request.quantity());
        }

        // Guardar el producto actualizado
        productRepository.save(product);

        // Registrar el movimiento
        StockMovement movement = StockMovement.builder()
                .type(request.type())
                .quantity(request.quantity())
                .notes(request.notes())
                .product(product)
                .build();

        return StockMovementResponse.from(movementRepository.save(movement));
    }
}
