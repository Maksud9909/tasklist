package com.example.tasklist.web.security;

import com.example.tasklist.domain.exception.ResourceNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {


    private final JwtTokenProvider jwtTokenProvider;



    @Override
    @SneakyThrows
    public void doFilter(
            final ServletRequest servletRequest,
            final ServletResponse servletResponse,
            final FilterChain filterChain){

        String bearerToken = ((HttpServletRequest)servletRequest).getHeader("Authorization");            // мы помещаем это в хедер
        if (bearerToken != null && bearerToken.startsWith("Bearer ")){
            bearerToken = bearerToken.substring(7);
        }

        if (bearerToken != null && jwtTokenProvider.validatedToken(bearerToken)){
            try {
                Authentication authentication = jwtTokenProvider.getAuthentication(bearerToken);
                if (authentication != null ){
                    SecurityContextHolder.getContext().setAuthentication(authentication); // мы авторизовали пользователя
                }
            }catch (ResourceNotFoundException ignored){} // если произошел такой exception мы не будем ничего не делать
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
