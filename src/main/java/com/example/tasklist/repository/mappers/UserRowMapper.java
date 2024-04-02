package com.example.tasklist.repository.mappers;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRowMapper {
    @SneakyThrows
    public static User mapRow(ResultSet resultSet){
        Set<Role> roles = new HashSet<>();
        while (resultSet.next()){
            roles.add(Role.valueOf(resultSet.getString("user_role_role")));
        }
        resultSet.beforeFirst(); // возвращемся в начало resultSet
        List<Task> tasks = TaskRowMapper.mapRows(resultSet); // здесь у нас хранятся список Tasks

        resultSet.beforeFirst(); // возвращемся в начало resultSet
        if (resultSet.next()){
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("user_name"));
            user.setUsername(resultSet.getString("user_username"));
            user.setPassword(resultSet.getString("user_password"));
            user.setRoles(roles);
            user.setTasks(tasks);
            return user;
        }
        return null;
    }
}
