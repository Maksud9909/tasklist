package com.example.tasklist.repository;

import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserById(Long id); // Если пользователь существует, он будет обернут в Optional, в противном случае Optional будет пустым.

    // Если пользователь существует, он будет обернут в Optional, в противном случае Optional будет пустым.
    Optional<User> findUserByUserName(String username);

    void updateUser(User user); // она будет добавлять юзера, или update если он уже есть.

    void createUser(User user); // он создает нового юзера

    void insertUserRole(Long userId, Role role); // роль которую будем давать юзеру

    // он будет проверять, является ли юзер владельцом этого задания. Также, это создано, чтобы другие юзеры не имели доступ к заданию.
    boolean isUserTaskOwner(Long userId, Long taskId);

    void deleteUser(Long id);
}
