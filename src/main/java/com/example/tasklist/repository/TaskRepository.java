package com.example.tasklist.repository;

import com.example.tasklist.domain.task.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Optional<Task> findTaskById(Long id); // Если задача существует, она будет обернута в Optional, в противном случае Optional будет пустым.

    List<Task> findAllTasksByUserId(Long userId);// Этот метод предполагает, что каждая задача привязана к конкретному пользователю.

    void assignTasksToUserById(Long taskId, Long userId); // он будет соединять юзера и таску

    void updateTask(Task task); // метод, который обновляет информацию об задаче

    void createTask(Task task); // он добавляет задачу в базу данных

    void deleteTask(Long id); // удаляет задачу через ее айди
}
