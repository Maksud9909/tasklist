package com.example.tasklist.repository.mappers;

import com.example.tasklist.domain.task.Status;
import com.example.tasklist.domain.task.Task;
import lombok.SneakyThrows;

import javax.swing.event.ListDataEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/*
Мы отсюда получаем результаты с базы, затем переводим их в Java объекты
 */
public class TaskRowMapper {

    @SneakyThrows // если будет ошибка он будет ее выбрасывать выше
    public static Task mapRow(ResultSet resultSet) {
        if (resultSet.next()){
            Task task = new Task();
            task.setId(resultSet.getLong("task_id"));
            task.setTitle(resultSet.getString("tasks_title"));
            task.setDescription(resultSet.getString("tasks_description"));
            task.setStatus(Status.valueOf(resultSet.getString("tasks_status"))); // из-за того, что у нас типа данных Enum



            Timestamp timestamp = resultSet.getTimestamp("tasks_expiration_date");
            if (timestamp != null){
                task.setExpirationDate(timestamp.toLocalDateTime());
            }

            return task;
        }
        return null;
    }


    @SneakyThrows
    public static List<Task> mapRows(ResultSet resultSet) {
        List<Task> tasks = new ArrayList<>();

        while (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getLong("task_id"));
            if (!resultSet.wasNull()) {
                task.setTitle(resultSet.getString("tasks_title"));
                task.setDescription(resultSet.getString("tasks_description"));
                task.setStatus(Status.valueOf(resultSet.getString("tasks_status"))); // из-за того, что у нас типа данных Enum


                Timestamp timestamp = resultSet.getTimestamp("tasks_expiration_date");
                if (timestamp != null) {
                    task.setExpirationDate(timestamp.toLocalDateTime());
                }

                tasks.add(task);
            }
        }
        return null;
    }


}
