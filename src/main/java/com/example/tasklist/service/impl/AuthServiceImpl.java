package com.example.tasklist.service.impl;

import com.example.tasklist.domain.user.User;
import com.example.tasklist.service.AuthService;
import com.example.tasklist.service.UserService;
import com.example.tasklist.web.dto.auth.JwtRequest;
import com.example.tasklist.web.dto.auth.JwtResponse;
import com.example.tasklist.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager; // авторизация во время логина
    private final UserService userService; // чтобы брать самих юзеров
    private final JwtTokenProvider jwtTokenProvider; // для создания токенов
    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword())); // авторизация

        User user = userService.getByUserName(loginRequest.getUserName());
        jwtResponse.setId(user.getId());
        jwtResponse.setUserName(user.getUserName());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(), user.getUserName(), user.getRoles()));
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getId(), user.getUserName()));

        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }
}
