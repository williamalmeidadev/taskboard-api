package com.williamdev.taskboard.api.dto;

import com.williamdev.taskboard.api.entity.Task;
import com.williamdev.taskboard.api.enums.TaskPriority;
import com.williamdev.taskboard.api.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDTO(
        UUID id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        UUID categoryId,
        LocalDateTime createdAt
) {
    public static TaskResponseDTO fromEntity(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getCategory() != null ? task.getCategory().getId() : null,
                task.getCreatedAt()
        );
    }
}
