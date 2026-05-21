package com.inventory.inventory_backend.service;
import com.inventory.inventory_backend.dto.ProductRequest;
import com.inventory.inventory_backend.dto.ProductResponse;
import com.inventory.inventory_backend.entity.Category;
import com.inventory.inventory_backend.entity.Product;
import com.inventory.inventory_backend.entity.Supplier;
import com.inventory.inventory_backend.exception.BusinessException;
import com.inventory.inventory_backend.exception.ResourceNotFoundException;
import com.inventory.inventory_backend.repository.CategoryRepository;
import com.inventory.inventory_backend.repository.ProductRepository;
import com.inventory.inventory_backend.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    public ProductResponse findById(Long id) {
        return ProductResponse.from(getProductOrThrow(id));
    }

    public List<ProductResponse> findByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    public List<ProductResponse> findBySupplier(Long supplierId) {
        return productRepository.findBySupplierId(supplierId)
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    // Alerta de stock bajo: productos donde stockQuantity <= minStockAlert
    public List<ProductResponse> findLowStock() {
        return productRepository.findLowStockProducts()
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    public List<ProductResponse> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(ProductResponse::from)
                .toList();
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        if (request.sku() != null && productRepository.existsBySku(request.sku())) {
            throw new BusinessException("Ya existe un producto con el SKU: " + request.sku());
        }

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .sku(request.sku())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .minStockAlert(request.minStockAlert())
                .build();

        // Asignar categoría si se indicó
        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría", request.categoryId()));
            product.setCategory(category);
        }

        // Asignar proveedor si se indicó
        if (request.supplierId() != null) {
            Supplier supplier = supplierRepository.findById(request.supplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Proveedor", request.supplierId()));
            product.setSupplier(supplier);
        }

        return ProductResponse.from(productRepository.save(product));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product product = getProductOrThrow(id);

        // Verificar SKU duplicado sólo si cambió
        if (request.sku() != null && !request.sku().equals(product.getSku())) {
            if (productRepository.existsBySku(request.sku())) {
                throw new BusinessException("Ya existe un producto con el SKU: " + request.sku());
            }
        }

        product.setName(request.name());
        product.setDescription(request.description());
        product.setSku(request.sku());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());
        product.setMinStockAlert(request.minStockAlert());

        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría", request.categoryId()));
            product.setCategory(category);
        } else {
            product.setCategory(null);
        }

        if (request.supplierId() != null) {
            Supplier supplier = supplierRepository.findById(request.supplierId())
                    .orElseThrow(() -> new ResourceNotFoundException("Proveedor", request.supplierId()));
            product.setSupplier(supplier);
        } else {
            product.setSupplier(null);
        }

        return ProductResponse.from(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        Product product = getProductOrThrow(id);
        productRepository.delete(product);
    }

    // Método privado de ayuda para no repetir el orElseThrow
    private Product getProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
    }
}
