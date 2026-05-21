package com.inventory.inventory_backend.repository;

import com.inventory.inventory_backend.entity.StockMovement;
import com.inventory.inventory_backend.enums.MovementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByProductIdOrderByCreatedAtDesc(Long productId);
    List<StockMovement> findByType(MovementType type);
}
