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

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse login(final JwtRequest loginRequest) {
        JwtResponse jwtResponse = new JwtResponse();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserName(),
                            loginRequest.getPassword()
                    )
            );
            User user = userService.getByUsername(loginRequest.getUserName());
            jwtResponse.setId(user.getId());
            jwtResponse.setUsername(user.getUsername());
            jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(
                    user.getId(),
                    user.getUsername(),
                    user.getRoles()
            ));
            jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(
                    user.getId(),
                    user.getUsername()
            ));
        } catch (Exception e) {
            // Обработка ошибки аутентификации
            // Например, возврат сообщения об ошибке или логирование
            e.printStackTrace();
            // В данном случае можно вернуть null или пустой объект JwtResponse,
            // так как аутентификация не удалась
            return null;
        }
        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(final String refreshToken) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }



//    @Override
//    public JwtResponse login(
//            final JwtRequest loginRequest
//    ) {
//        JwtResponse jwtResponse = new JwtResponse();
//        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
//        User user = userService.getByUsername(loginRequest.getUserName());
//        jwtResponse.setId(user.getId());
//        jwtResponse.setUsername(user.getUsername());
//        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(),
//                user.getUsername(), user.getRoles()));
//        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(
//                user.getId(), user.getUsername())
//        );
//        return jwtResponse;
//    }

}