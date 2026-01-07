package com.williamdev.taskboard.api.service;

import com.williamdev.taskboard.api.dto.CategoryResponseDTO;
import com.williamdev.taskboard.api.entity.Category;
import com.williamdev.taskboard.api.exception.CategoryAlreadyExistsException;
import com.williamdev.taskboard.api.exception.CategoryNotFoundException;
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
            throw new CategoryAlreadyExistsException("Category already exists");
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
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        return CategoryResponseDTO.fromEntity(category);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found");
        }
        repository.deleteById(id);
    }
}
