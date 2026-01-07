package com.williamdev.taskboard.api.repository;

import com.williamdev.taskboard.api.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByCategoryId(UUID categoryId);

}
