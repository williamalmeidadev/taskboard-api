package com.williamdev.taskboard.api.service;

import com.williamdev.taskboard.api.dto.TaskRequestDTO;
import com.williamdev.taskboard.api.dto.TaskResponseDTO;
import com.williamdev.taskboard.api.entity.Category;
import com.williamdev.taskboard.api.entity.Task;
import com.williamdev.taskboard.api.exception.CategoryNotFoundException;
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
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        Task task = new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setStatus(dto.status());
        task.setPriority(dto.priority());
        task.setCategory(category);

        return TaskResponseDTO.fromEntity(taskRepository.save(task));
    }

    public List<TaskResponseDTO> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(TaskResponseDTO::fromEntity)
                .toList();
    }

    public List<TaskResponseDTO> findByCategory(UUID categoryId) {
        return taskRepository.findByCategoryId(categoryId)
                .stream()
                .map(TaskResponseDTO::fromEntity)
                .toList();
    }

    public void delete(UUID id) {
        taskRepository.deleteById(id);
    }
}
