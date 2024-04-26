package com.example.tasklist.repository;

import com.example.tasklist.domain.task.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Optional;
@Mapper
public interface TaskRepository {
    Optional<Task> findTaskById(Long id); // Если задача существует, она будет обернута в Optional, в противном случае Optional будет пустым.

    List<Task> findAllTasksByUserId(Long userId);// Этот метод предполагает, что каждая задача привязана к конкретному пользователю.
    // аннотция param, сделана для того, чтобы myBatis мог его прочитать
    void assignTasksToUserById(@Param("taskId") Long taskId,@Param("userId") Long userId); // он будет соединять юзера и таску

    void updateTask(Task task); // метод, который обновляет информацию об задаче

    void createTask(Task task); // он добавляет задачу в базу данных

    void deleteTask(Long id); // удаляет задачу через ее айди
}
