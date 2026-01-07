package com.williamdev.taskboard.api.service;

import com.williamdev.taskboard.api.dto.CategoryResponseDTO;
import com.williamdev.taskboard.api.entity.Category;
import com.williamdev.taskboard.api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryResponseDTO create(String name) {
        if (repository.existsByName(name)) {
            throw new IllegalArgumentException("Category already exists");
        }

        Category category = new Category();
        category.setName(name);

        Category saved = repository.save(category);
        return CategoryResponseDTO.fromEntity(saved);
    }

    public List<CategoryResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(CategoryResponseDTO::fromEntity)
                .toList();
    }

    public CategoryResponseDTO findById(UUID id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        return CategoryResponseDTO.fromEntity(category);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Category not found");
        }
        repository.deleteById(id);
    }
}
