package com.inventory.inventory_backend.repository;

import com.inventory.inventory_backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);

    // Todos los productos de una categoría
    List<Product> findByCategoryId(Long categoryId);

    // Todos los productos de un proveedor
    List<Product> findBySupplierId(Long supplierId);

    // Productos con stock igual o por debajo del mínimo (alertas)
    // JPQL: usamos nombres de campos de la entidad, no de la tabla
    @Query("SELECT p FROM Product p WHERE p.stockQuantity <= p.minStockAlert")
    List<Product> findLowStockProducts();

    // Búsqueda por nombre (contiene, sin importar mayúsculas)
    List<Product> findByNameContainingIgnoreCase(String name);

    boolean existsBySku(String sku);
}
