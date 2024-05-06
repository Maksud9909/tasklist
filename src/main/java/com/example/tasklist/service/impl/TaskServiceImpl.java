package com.example.tasklist.service.impl;

import com.example.tasklist.domain.exception.ResourceNotFoundException;
import com.example.tasklist.domain.task.Status;
import com.example.tasklist.domain.task.Task;
import com.example.tasklist.repository.TaskRepository;
import com.example.tasklist.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true) // когда идут get запросы мы пишем read only true
    @Cacheable(value = "TaskService::getById", key = "#id")
    public Task getById(Long id) {
        return taskRepository.findTaskById(id).orElseThrow(()->new ResourceNotFoundException("Task not found"));
    }

    @Override
    @Transactional(readOnly = true) // когда идут get запросы мы пишем read only true
    // в этом методе кэшировать не выгодно, из-за того, что мы возвращаем List<Task>, их постоянно обновлять не выгодно
    public List<Task> getAllByUserId(Long id) {
        return taskRepository.findAllTasksByUserId(id);
    }

    @Override
    @Transactional
    @CachePut(value = "TaskService::getById", key = "#task.id")
    public Task update(Task task) {
        if (task.getStatus() == null){
            task.setStatus(Status.TODO);
        }
        taskRepository.updateTask(task);
        return task;
    }

    @Override
    @Transactional
    @Cacheable(value = "TaskService::getById", key = "#task.id") // поместиться в кэш сразу после создания
    public Task create(Task task,Long userId) {
        task.setStatus(Status.TODO); // когда только создается задача, то сразу стоит to do
        taskRepository.createTask(task);
        taskRepository.assignTasksToUserById(task.getId(),userId); // соеденяем таск с юзером
        return task;
    }


    @Override
    @Transactional
    @CacheEvict(value = "TaskService::getById", key = "#id") // поместиться в кэш сразу после удаления
    public void delete(Long id) {
        taskRepository.deleteTask(id);
    }
}
