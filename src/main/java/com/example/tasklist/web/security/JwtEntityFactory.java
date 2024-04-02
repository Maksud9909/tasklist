package com.example.tasklist.web.security;

import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// будет реализовывать паттерн factory
/**
 * Фабричный класс для создания экземпляров JwtEntity из сущностей User.
 * Реализует паттерн фабрики для инкапсуляции логики создания.
 */
public class JwtEntityFactory {

    /**
     * Создает экземпляр JwtEntity из сущности User.
     *
     * @param user Сущность пользователя, из которой создается JwtEntity.
     * @return JwtEntity, представляющий пользователя для аутентификации через JWT.
     */
    public static JwtEntity create(
            final User user
    ) {
        return new JwtEntity(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getPassword(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
        );
    }


    private static List<GrantedAuthority> mapToGrantedAuthorities(
            final List<Role> roles
    ) {
        return roles.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
