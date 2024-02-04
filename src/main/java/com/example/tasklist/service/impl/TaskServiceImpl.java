package com.example.tasklist.service.impl;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TaskServiceImpl implements TaskService {
    @Override
    public Task getById(Long id) {
        return null;
    }

    @Override
    public List<Task> getAllByUserId(String taskName) {
        return null;
    }

    @Override
    public Task update(Task user) {
        return null;
    }

    @Override
    public Task create(Task user) {
        return null;
    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        return false;
    }

    @Override
    public void delete(Long id) {

    }
}
