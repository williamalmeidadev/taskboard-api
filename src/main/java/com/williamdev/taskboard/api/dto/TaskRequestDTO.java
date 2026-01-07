package com.williamdev.taskboard.api.dto;

import com.williamdev.taskboard.api.enums.TaskPriority;
import com.williamdev.taskboard.api.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TaskRequestDTO(
        @NotBlank String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        @NotNull UUID categoryId
) {}
