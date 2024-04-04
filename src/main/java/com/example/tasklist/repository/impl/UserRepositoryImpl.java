package com.example.tasklist.repository.impl;

import com.example.tasklist.domain.exception.ResourceMappingException;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.repository.dataSourceConfig.DataSourceConfig;
import com.example.tasklist.repository.UserRepository;
import com.example.tasklist.repository.mappers.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


/**
 * Implementation of the {@link UserRepository} interface for handling User entities.
 */
//@Repository
@RequiredArgsConstructor

public class UserRepositoryImpl implements UserRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            select u.id as user_id,
                   u.name as user_name,
                   u.username as user_username,
                   u.password as user_password,
                   ur.role as user_role_role,
                   t.id as task_id,
                   t.title as task_title,
                   t.description as task_description,
                   t.expirationdate as task_expiration_date,
                   t.status as task_status
            from users u
            left join public.users_role ur on u.id = ur.user_id
            left join public.users_tasks ut on u.id = ut.user_id
            left join public.tasks t on ut.task_id = t.id
            where u.id = ?
            """;

    private final String FIND_BY_USERNAME = """
            select u.id as user_id,
                           u.name as user_name,
                           u.username as user_username,
                           u.password as user_password,
                           ur.role as user_role_role,
                           t.id as task_id,
                           t.title as task_title,
                           t.description as task_description,
                           t.expirationdate as task_expiration_date,
                           t.status as task_status
                    from users u
                    left join users_role ur on u.id = ur.user_id
                    left join users_tasks ut on u.id = ut.user_id
                    left join tasks t on ut.task_id = t.id
                    where u.username = ?
        """;



    private final String UPDATE = """
            UPDATE users
            SET name = ?,
                username = ?,
                password = ?
            WHERE id = ?           
           """;


    private final String DELETE = """
            delete from users where id = ?
            """;
    private final String CREATE = """
            insert into users (name, username, password)
            values (?,?,?)
            """;

    private final String INSER_USER_ROLE = """
            INSERT INTO users_role (user_id, role)
            values (?,?)
            """;

    private final String IS_TASK_OWNER = """
            SELECT exists(
                SELECT 1
                FROM users_tasks
                WHERE user_id = ?
                AND task_id = ?
            )
            """;

    @Override
    public Optional<User> findUserById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            // мы написали это так как в резалт сете нужно возвращаться потом вперед.
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            statement.setLong(1,id);
            try (ResultSet resultSet = statement.executeQuery()){
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding UserById.");
        }

    }

    @Override
    public Optional<User> findUserByUserName(String username) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            // мы написали это так как в резалт сете нужно возвращаться потом вперед.
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            statement.setString(1, username);
            System.out.println("We are in");
            try (ResultSet resultSet = statement.executeQuery()){
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding UserByUsername.");
        }

    }

    @Override
    public void updateUser(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1,user.getName());
            statement.setString(2,user.getUsername());
            statement.setString(3,user.getPassword());
            statement.setLong(4,user.getId());
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while updating User");
        }
    }

    @Override
    public void createUser(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE,PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1,user.getName());
            statement.setString(2,user.getUsername());
            statement.setString(3,user.getPassword());

            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()){ // получаем отсюда айдишник
                resultSet.next(); // он идет в самое начало где стоит айди
                user.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while creating User");
        }
    }
    /**
     * Inserts a new Role for a User.
     *
     * @param userId The unique identifier of the User.
     * @param role   The Role to be assigned to the User.
     */
    @Override
    public void insertUserRole(Long userId, Role role) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSER_USER_ROLE);
            statement.setLong(1,userId);
            statement.setString(2,role.name());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while insertUserRole");
        }
    }

    @Override
    public boolean isUserTaskOwner(Long userId, Long taskId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(IS_TASK_OWNER,PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1,userId);
            statement.setLong(2,taskId);

            try (ResultSet resultSet = statement.executeQuery()){
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while checking if user is a task owner");
        }
    }

    @Override
    public void deleteUser(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1,id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while deleting the User");
        }
    }
}
