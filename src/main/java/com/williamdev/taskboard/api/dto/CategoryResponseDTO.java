package com.williamdev.taskboard.api.dto;

import com.williamdev.taskboard.api.entity.Category;
import java.time.LocalDateTime;
import java.util.UUID;

public record CategoryResponseDTO(UUID id, String name, LocalDateTime createdAt) {
    public static CategoryResponseDTO fromEntity(Category category) {
        return new CategoryResponseDTO(
            category.getId(),
            category.getName(),
            category.getCreatedAt()
        );
    }
}
