package com.example.tasklist.web.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Представляет сущность пользователя для аутентификации через JWT.
 * Реализует интерфейс UserDetails Spring Security для интеграции с Spring Security.
 */
@Data
@AllArgsConstructor
public class JwtEntity implements UserDetails {

    private Long id;
    private final String userName;
    private final String name;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities; // Для кого пишется этот метод для админа, или юзера
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
