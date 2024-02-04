package com.example.tasklist.service;

import com.example.tasklist.domain.task.Task;

import java.util.List;


public interface TaskService {
    Task getById(Long id);

    List<Task> getAllByUserId(String taskName);

    Task update(Task task);

    Task create(Task task);

    boolean isTaskOwner(Long userId,Long taskId);

    void delete(Long id);
}
