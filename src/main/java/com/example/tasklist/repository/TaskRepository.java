package com.example.tasklist.repository;

import com.example.tasklist.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Optional<Task> findById(Long id);

    List<Task> findAllByUserId(Long userId);

    void assignToUserById(Long taskId,Long userId); // он будет соединять юзера и таску

    void update(Task task);

    void create(Task task);

    void delete(Long id);
}
