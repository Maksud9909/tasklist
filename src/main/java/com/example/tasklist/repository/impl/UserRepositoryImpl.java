package com.example.tasklist.repository.impl;

import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.repository.UserRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


/**
 * Implementation of the {@link UserRepository} interface for handling User entities.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUserName(String userName) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void create(User user) {

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
    public boolean isTaskOwner(Long userId, Long taskId) {
        return false;
    }

    @Override
    public void delete(Long id) {

    }
}
