package com.inventory.inventory_backend.service;

import com.inventory.inventory_backend.dto.CategoryRequest;
import com.inventory.inventory_backend.dto.CategoryResponse;
import com.inventory.inventory_backend.entity.Category;
import com.inventory.inventory_backend.exception.BusinessException;
import com.inventory.inventory_backend.exception.ResourceNotFoundException;
import com.inventory.inventory_backend.repository.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor            // Lombok genera el constructor con los campos final
@Transactional(readOnly = true)     // Por defecto todas las operaciones son de sólo lectura
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::from)
                .toList();
    }

    public CategoryResponse findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", id));
        return CategoryResponse.from(category);
    }

    @Transactional  // Sobreescribe readOnly=true para operaciones de escritura
    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new BusinessException("Ya existe una categoría con el nombre: " + request.name());
        }
        Category category = Category.builder()
                .name(request.name())
                .description(request.description())
                .build();
        return CategoryResponse.from(categoryRepository.save(category));
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", id));

        // Verificar que el nuevo nombre no lo use OTRA categoría
        categoryRepository.findByName(request.name())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new BusinessException("Ya existe una categoría con el nombre: " + request.name());
                    }
                });

        category.setName(request.name());
        category.setDescription(request.description());
        return CategoryResponse.from(categoryRepository.save(category));
    }

    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría", id));
        if (!category.getProducts().isEmpty()) {
            throw new BusinessException("No se puede eliminar la categoría porque tiene productos asociados");
        }
        categoryRepository.delete(category);
    }
}
