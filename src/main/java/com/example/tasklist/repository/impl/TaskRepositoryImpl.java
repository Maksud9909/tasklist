package com.example.tasklist.repository.impl;

import com.example.tasklist.domain.exception.ResourceMappingException;
import com.example.tasklist.domain.task.Task;
import com.example.tasklist.repository.DataSourceConfig;
import com.example.tasklist.repository.TaskRepository;
import com.example.tasklist.repository.mappers.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
/**
 * Implementation of the {@link TaskRepository} interface for handling Task entities.
 */
@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final DataSourceConfig dataSourceConfig;


    // тут мы находим таск через айди
    private final String FIND_BY_ID = """
            select t.id as task_id,
                   t.title as tasks_title,
                   t.description as tasks_description,
                   t.expirationdate as tasks_expiration_date,
                   t.status as tasks_status
                   from tasks t where id = ?""";

    private final String FIND_ALL_BY_USER_ID = """
       select t.id as task_id,
       t.title as tasks_title,
       t.description as tasks_description,
       t.expirationdate as tasks_expiration_date,
       t.status as tasks_status
       from tasks t
join public.users_tasks ut on t.id = ut.task_id
       where ut.user_id = ?
            """;

    private final String ASSIGN = """
            insert into users_tasks (user_id, task_id)
            values (?,?)
            """;

    private final String DELETE = """
            delete from tasks where id = ?""";

    private final String UPDATE = """
            update tasks
            set title = ?,
                description = ?,
                expirationdate = ?,
                status = ?
            where id = ?""";


    private final String CREATE = """
            insert into tasks (id, title, description, status, expirationdate)
            values (?,?,?,?)""";

    @Override
    public Optional<Task> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection(); // получаем подключение к базе
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1,id); // мы передали айди в ?
            try(ResultSet resultSet = statement.executeQuery()){
                return Optional.ofNullable(TaskRowMapper.mapRow(resultSet)); // получаем Java объект
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding user by id.");
        }
    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection(); // получаем подключение к базе
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1,userId); // мы передали айди в ?
            try(ResultSet resultSet = statement.executeQuery()){
                return TaskRowMapper.mapRows(resultSet);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding all by user id");

        }
    }

    /**
     * Assigns a Task to a User by their respective identifiers.
     *
     * @param taskId The unique identifier of the Task.
     * @param userId The unique identifier of the User.
     */
    @Override
    public void assignToUserById(Long taskId, Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection(); // получаем подключение к базе
            PreparedStatement statement = connection.prepareStatement(ASSIGN);
            statement.setLong(1,taskId); // мы передали айди в ?
            statement.setLong(2,userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while assign to user");

        }
    }

    @Override
    public void update(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection(); // получаем подключение к базе
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1,task.getTitle());
            if (task.getDescription() == null){
                statement.setNull(2, Types.VARCHAR); // если null, то просто пустой стринг
            }else {
                statement.setString(2, task.getDescription());
            }
            if (task.getExpirationDate() == null){
                statement.setNull(3, Types.TIMESTAMP); // если null, то просто пустой стринг
            }else {
                statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationDate()));
            }


            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while updating to user");

        }
    }

    @Override
    public void create(Task task) {

    }

    @Override
    public void delete(Long id) {

    }
}
