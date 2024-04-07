package com.example.tasklist.web.security.exxpression.easyExpression;

import com.example.tasklist.domain.user.Role;
import com.example.tasklist.service.UserService;
import com.example.tasklist.web.security.JwtEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression") // мы написали название бина
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final UserService userService;

    public boolean canAccessUser(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtEntity user = (JwtEntity) authentication.getPrincipal(); // user with UserDetails
        Long userId = user.getId(); // получаем юзера через айди, уже авторизованного


        // и тут мы сравниваем юзера который авторизованного и айди который получили, либо админ
        return userId.equals(id) || hasAnyRole(authentication, Role.ROLE_ADMIN);
    }


    private boolean hasAnyRole(Authentication authentication, Role... roles){
        for (Role role: roles){
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name()); // мы получаем authority исходя из этой роли
            if (authentication.getAuthorities().contains(authority)){
                return true; // если они совпали
            }
        }
        return false;
    }

    public boolean canAccessTask(Long taskId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtEntity user = (JwtEntity) authentication.getPrincipal(); // user with UserDetails
        Long userId = user.getId();

        return userService.isTaskOwner(userId,taskId);
    }

}
