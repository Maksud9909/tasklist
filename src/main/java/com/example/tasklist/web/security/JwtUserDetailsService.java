package com.example.tasklist.web.security;

import com.example.tasklist.domain.user.User;
import com.example.tasklist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * Реализация интерфейса UserDetailsService Spring Security.
 * Загружает детали пользователя из базы данных на основе предоставленного имени пользователя.
 */
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;


    /**
     * Загружает детали пользователя по имени пользователя.
     *
     * @param username Имя пользователя, для которого загружаются детали пользователя.
     * @return UserDetails, представляющий пользователя для аутентификации.
     * @throws UsernameNotFoundException Если пользователь с указанным именем пользователя не найден.
     * То есть
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUserName(username);
        return JwtEntityFactory.create(user);
    }
}
