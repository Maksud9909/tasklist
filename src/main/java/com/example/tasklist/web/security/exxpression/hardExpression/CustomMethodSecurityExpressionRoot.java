package com.example.tasklist.web.security.exxpression.hardExpression;

import com.example.tasklist.domain.user.Role;
import com.example.tasklist.service.UserService;
import com.example.tasklist.web.security.JwtEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;



@Setter
@Getter
/**
 * Тут мы этими классами, доходим до самого корня всех SecurityExxpressions через спринг.
 */
public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;
    private Object target;
    private HttpServletRequest request;
    private UserService userService;

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

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }


    @Override
    public Object getThis() {
        return target;
    }
}






/**
 * На самом деле эти методы имплементриуются интерфейсом MethodSecurityExpressionOperations,
 * но у нас есть аннотация Setter, которая делает не нужным код внизу.
 */

//@Override
//    public void setFilterObject(Object filterObject) {
//        this.filterObject = filterObject;
//    }
//
//    @Override
//    public Object getFilterObject() {
//        return filterObject;
//    }
//
//    @Override
//    public void setReturnObject(Object returnObject) {
//        this.returnObject = returnObject;
//    }
//
//    @Override
//    public Object getReturnObject() {
//        return returnObject;
//    }