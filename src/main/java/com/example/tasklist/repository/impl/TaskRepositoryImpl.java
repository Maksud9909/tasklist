package com.example.tasklist.repository.impl;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.repository.TaskRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
/**
 * Implementation of the {@link TaskRepository} interface for handling Task entities.
 */
@Repository
public class TaskRepositoryImpl implements TaskRepository {

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        return null;
    }

    /**
     * Assigns a Task to a User by their respective identifiers.
     *
     * @param taskId The unique identifier of the Task.
     * @param userId The unique identifier of the User.
     */
    @Override
    public void assignToUserById(Long taskId, Long userId) {

    }

    @Override
    public void update(Task task) {

    }

    @Override
    public void create(Task task) {

    }

    @Override
    public void delete(Long id) {

    }
}
