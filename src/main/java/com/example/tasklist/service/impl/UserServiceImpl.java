package com.example.tasklist.service.impl;

import com.example.tasklist.domain.exception.ResourceNotFoundException;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.repository.UserRepository;
import com.example.tasklist.service.UserService;
import org.springframework.cache.annotation.*;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "UserService::getById", key = "#id")
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User nor found"));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getByUsername", key = "#username")
    public User getByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(()-> new ResourceNotFoundException("Username not found"));
    }

    @Override
    @Transactional
    @Caching(put = {
//             тут как юзер будет обновляться, то тогда он его данные обновятся в оперативке тоже
            @CachePut(value = "UserService::getById", key = "#user.id"), // при обновлении
            @CachePut(value = "UserService::getByUsername", key = "#user.username")
    })
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // хешированный пароль
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    @Caching(cacheable = { // при создание пишется cacheable
            @Cacheable(value = "UserService::getById", key = "#user.id"),
            @Cacheable(value = "UserService::getByUsername", key = "#user.username")
    })
    public User create(User user) {
        if (userRepository.findUserByUsername(user.getUsername()).isPresent()){ // есть ли такой пользователь в базе
            throw new IllegalStateException("User is already existing");
        }
        if (!user.getPassword().equals(user.getConfirmationOfPassword())){ // если ввести неправильный пароль
            throw new IllegalStateException("Password and passwordConfirmation do not match");
        }

        // в остальных случаях будет это
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        Set<Role> roles = Set.of(Role.ROLE_USER);

        user.setRoles(roles);
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::isTaskOwner", key = "#userId + '.' + #taskId")
    public boolean isTaskOwner(Long userId, Long taskId) {
        return userRepository.isUserTaskOwner(userId,taskId);
    }

    @Override
    @Transactional
    @CacheEvict(value = "UserService::getById", key = "#id")
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
