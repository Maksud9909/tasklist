package com.example.tasklist.repository.impl;

import com.example.tasklist.domain.exception.ResourceMappingException;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.repository.DataSourceConfig;
import com.example.tasklist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;


/**
 * Implementation of the {@link UserRepository} interface for handling User entities.
 */
@Repository
@RequiredArgsConstructor

public class UserRepositoryImpl implements UserRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            select u.id as user_id,
                   u.name as user_name,
                   u.username as user_username,
                   u.password as user_passeord,
                   ur.role as user_role,
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

    private final String DELETE = """
            delete from users where id = ?
            """;
    private final String CREATE = """
            insert into users (name, username, password)
            values (?,?,?)
            """;

    @Override
    public Optional<User> findUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByUserName(String userName) {
        return Optional.empty();
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void createUser(User user) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE,PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1,user.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while deleting the task");
        }
    }
    /**
     * Inserts a new Role for a User.
     *
     * @param userId The unique identifier of the User.
     * @param role   The Role to be assigned to the User.
     */
    @Override
    public void insertUserRole(long userId, Role role) {

    }

    @Override
    public boolean isUserTaskOwner(Long userId, Long taskId) {
        return false;
    }

    @Override
    public void deleteUser(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1,id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while deleting the task");
        }
    }
}
