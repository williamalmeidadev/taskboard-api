package com.williamdev.taskboard.api.service;

import com.williamdev.taskboard.api.dto.TaskRequestDTO;
import com.williamdev.taskboard.api.dto.TaskResponseDTO;
import com.williamdev.taskboard.api.entity.Category;
import com.williamdev.taskboard.api.entity.Task;
import com.williamdev.taskboard.api.exception.CategoryNotFoundException;
import com.williamdev.taskboard.api.exception.InvalidTaskDataException;
import com.williamdev.taskboard.api.exception.TaskNotFoundException;
import com.williamdev.taskboard.api.exception.TaskOperationException;
import com.williamdev.taskboard.api.repository.CategoryRepository;
import com.williamdev.taskboard.api.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    public TaskResponseDTO create(TaskRequestDTO dto) {
        if (dto.title() == null || dto.title().isBlank()) {
            throw new InvalidTaskDataException("Task title must not be empty");
        }
        if (dto.status() == null) {
            throw new InvalidTaskDataException("Task status must not be null");
        }
        if (dto.priority() == null) {
            throw new InvalidTaskDataException("Task priority must not be null");
        }
        Category category = null;
        if (dto.categoryId() != null) {
            category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        }
        try {
            Task task = new Task();
            task.setTitle(dto.title());
            task.setDescription(dto.description());
            task.setStatus(dto.status());
            task.setPriority(dto.priority());
            task.setCategory(category);
            return TaskResponseDTO.fromEntity(taskRepository.save(task));
        } catch (Exception e) {
            throw new TaskOperationException("Failed to create task: " + e.getMessage());
        }
    }

    public List<TaskResponseDTO> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(TaskResponseDTO::fromEntity)
                .toList();
    }

    public List<TaskResponseDTO> findByCategory(UUID categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException("Category not found");
        }
        return taskRepository.findByCategoryId(categoryId)
                .stream()
                .map(TaskResponseDTO::fromEntity)
                .toList();
    }

    public TaskResponseDTO findById(UUID id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        return TaskResponseDTO.fromEntity(task);
    }

    public void delete(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found");
        }
        try {
            taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new TaskOperationException("Failed to delete task: " + e.getMessage());
        }
    }

    public TaskResponseDTO update(UUID id, TaskRequestDTO dto) {
        if (dto.title() == null || dto.title().isBlank()) {
            throw new InvalidTaskDataException("Task title must not be empty");
        }
        if (dto.status() == null) {
            throw new InvalidTaskDataException("Task status must not be null");
        }
        if (dto.priority() == null) {
            throw new InvalidTaskDataException("Task priority must not be null");
        }
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));
        Category category = null;
        if (dto.categoryId() != null) {
            category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        }
        try {
            task.setTitle(dto.title());
            task.setDescription(dto.description());
            task.setStatus(dto.status());
            task.setPriority(dto.priority());
            task.setCategory(category);
            return TaskResponseDTO.fromEntity(taskRepository.save(task));
        } catch (Exception e) {
            throw new TaskOperationException("Failed to update task: " + e.getMessage());
        }
    }
}
