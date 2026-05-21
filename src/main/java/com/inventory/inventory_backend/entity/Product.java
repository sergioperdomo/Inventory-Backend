package com.inventory.inventory_backend.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 150)
    @Column(nullable = false, length = 150)
    private String name;

    @Size(max = 500)
    @Column(length = 500)
    private String description;

    // SKU: código único del producto (ej: "PROD-001")
    @Size (max = 50)
    @Column(unique = true, length = 50)
    private String sku;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    // Cantidad actual en stock
    @NotNull
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "stock_quantity", nullable = false)
    @Builder.Default
    private Integer stockQuantity = 0;

    // Umbral mínimo: cuando stock cae por debajo de este valor, hay alerta
    @NotNull
    @Min(value = 0)
    @Column(name = "min_stock_alert", nullable = false)
    @Builder.Default
    private Integer minStockAlert = 5;

    // Campo calculado (no persistido): true si stock <= minStockAlert
    @Transient
    public boolean isLowStock() {
        return this.stockQuantity <= this.minStockAlert;
    }

    // Relación con categoría (muchos productos → una categoría)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // Relación con proveedor (muchos productos → un proveedor)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    // Historial de movimientos de este producto
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<StockMovement> movements = new ArrayList<>();
}
