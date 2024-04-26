package com.example.tasklist.repository;

import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.Optional;


@Mapper
public interface UserRepository {
    Optional<User> findUserById(Long id); // Если пользователь существует, он будет обернут в Optional, в противном случае Optional будет пустым.

    // Если пользователь существует, он будет обернут в Optional, в противном случае Optional будет пустым.
    Optional<User> findUserByUserName(String username);

    void updateUser(User user); // она будет добавлять юзера, или update если он уже есть.

    void createUser(User user); // он создает нового юзера

    void insertUserRole(@Param("userId") Long userId, @Param("role")Role role); // роль которую будем давать юзеру

    // он будет проверять, является ли юзер владельцом этого задания. Также, это создано, чтобы другие юзеры не имели доступ к заданию.
    boolean isUserTaskOwner(@Param("userId")Long userId, @Param("taskId")Long taskId);

    void deleteUser(Long id);
}
