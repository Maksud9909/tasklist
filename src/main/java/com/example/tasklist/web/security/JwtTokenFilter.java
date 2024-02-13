package com.example.tasklist.web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {


    private final JwtTokenProvider tokenProvider;



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String bearerToken = ((HttpServletRequest)servletRequest).getHeader("Authorization");            // мы помещаем это в хедер
        if (bearerToken != null && bearerToken.startsWith("Bearar ")){
            bearerToken = bearerToken.substring(7);
        }
        if (bearerToken != null && tokenProvider.validatedToken(bearerToken)){
            try {
                Authentication authentication = tokenProvider.getAuthentication(bearerToken);
                if (authentication != null ){
                    SecurityContextHolder.getContext().setAuthentication(authentication); // мы авторизовали пользователя
                }
            }
        }
    }
}
