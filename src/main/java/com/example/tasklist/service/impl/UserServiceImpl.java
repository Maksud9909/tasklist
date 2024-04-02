package com.example.tasklist.service.impl;

import com.example.tasklist.domain.exception.ResourceNotFoundException;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.repository.UserRepository;
import com.example.tasklist.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // так как будем кодировать пароль
    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findUserById(id).orElseThrow(()-> new ResourceNotFoundException("User nor found"));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findUserByUserName(username).orElseThrow(()-> new ResourceNotFoundException("Username not found"));
    }

    @Override
    @Transactional
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // хешированный пароль
        userRepository.updateUser(user);
        return user;
    }

    @Override
    @Transactional
    public User create(User user) {
        if (userRepository.findUserByUserName(user.getUsername()).isPresent()){ // есть ли такой пользователь в базе
            throw new IllegalStateException("User is already existing");
        }
        if (!user.getPassword().equals(user.getConfirmationOfPassword())){ // если ввести неправильный пароль
            throw new IllegalStateException("Password and passwordConfirmation do not match");
        }

        // в остальных случаях будет это
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.createUser(user);


        Set<Role> roles = Set.of(Role.ROLE_USER);
        userRepository.insertUserRole(user.getId(), Role.ROLE_USER); // по дефолту при рег идет юзер
        user.setRoles(roles);

        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isTaskOwner(Long userId, Long taskId) {
        return userRepository.isUserTaskOwner(userId,taskId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteUser(id);
    }
}
