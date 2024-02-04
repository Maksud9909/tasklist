package com.example.tasklist.repository;

import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id); // interface выводит Optional, сервис уже выдает объект, а дальше уже смотрят exception или нет

    Optional<User> findByUserName(String userName);

    void update(User user); // она будет добавлять юзера, или update если он уже есть.

    void create(User user); // он создает нового юзера

    void insertUserRole(long userId, Role role); // роль которую будем давать юзеру

    // он будет проверять, является ли юзер владельцом этого задания. Также, это создано, чтобы другие юзеры не имели доступ к заданию.
    boolean isTaskOwner(Long userId,Long taskId);

    void delete(Long id);
}
