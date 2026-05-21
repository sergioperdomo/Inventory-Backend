package com.inventory.inventory_backend.service;
import com.inventory.inventory_backend.dto.SupplierRequest;
import com.inventory.inventory_backend.dto.SupplierResponse;
import com.inventory.inventory_backend.entity.Supplier;
import com.inventory.inventory_backend.exception.BusinessException;
import com.inventory.inventory_backend.exception.ResourceNotFoundException;
import com.inventory.inventory_backend.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public List<SupplierResponse> findAll() {
        return supplierRepository.findAll().stream().map(SupplierResponse::from).toList();
    }

    public SupplierResponse findById(Long id) {
        return SupplierResponse.from(getOrThrow(id));
    }

    @Transactional
    public SupplierResponse create(SupplierRequest request) {
        if (request.email() != null && supplierRepository.existsByName(request.email())) {
            throw new BusinessException("Ya existe un proveedor con el email: " + request.email());
        }
        Supplier supplier = Supplier.builder()
                .name(request.name())
                .contactName(request.contactName())
                .email(request.email())
                .phone(request.phone())
                .address(request.address())
                .build();
        return SupplierResponse.from(supplierRepository.save(supplier));
    }

    @Transactional
    public SupplierResponse update(Long id, SupplierRequest request) {
        Supplier supplier = getOrThrow(id);
        supplier.setName(request.name());
        supplier.setContactName(request.contactName());
        supplier.setEmail(request.email());
        supplier.setPhone(request.phone());
        supplier.setAddress(request.address());
        return SupplierResponse.from(supplierRepository.save(supplier));
    }

    @Transactional
    public void delete(Long id) {
        Supplier supplier = getOrThrow(id);
        if (!supplier.getProducts().isEmpty()) {
            throw new BusinessException("No se puede eliminar el proveedor porque tiene productos asociados");
        }
        supplierRepository.delete(supplier);
    }

    private Supplier getOrThrow(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor", id));
    }
}
