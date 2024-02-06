package com.example.tasklist.repository;

import com.example.tasklist.domain.task.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Optional<Task> findById(Long id); // Если задача существует, она будет обернута в Optional, в противном случае Optional будет пустым.

    List<Task> findAllByUserId(Long userId);// Этот метод предполагает, что каждая задача привязана к конкретному пользователю.

    void assignToUserById(Long taskId,Long userId); // он будет соединять юзера и таску

    void update(Task task); // метод, который обновляет информацию об задаче

    void create(Task task); // он добавляет задачу в базу данных

    void delete(Long id); // удаляет задачу через ее айди
}
