package com.example.tasklist.repository;

import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id); // Если пользователь существует, он будет обернут в Optional, в противном случае Optional будет пустым.

    // Если пользователь существует, он будет обернут в Optional, в противном случае Optional будет пустым.
    Optional<User> findByUserName(String userName);

    void update(User user); // она будет добавлять юзера, или update если он уже есть.

    void create(User user); // он создает нового юзера

    void insertUserRole(long userId, Role role); // роль которую будем давать юзеру

    // он будет проверять, является ли юзер владельцом этого задания. Также, это создано, чтобы другие юзеры не имели доступ к заданию.
    boolean isTaskOwner(Long userId,Long taskId);

    void delete(Long id);
}
