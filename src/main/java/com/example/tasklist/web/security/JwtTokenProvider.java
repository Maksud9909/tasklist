package com.example.tasklist.web.security;

import com.example.tasklist.service.props.JwtProperties;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
}
