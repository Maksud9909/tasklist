package com.example.tasklist.web.security;

import com.example.tasklist.domain.exception.AccessDeniedException;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.service.UserService;
import com.example.tasklist.service.props.JwtProperties;
import com.example.tasklist.web.dto.auth.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final UserDetailsService  userDetailsService;
    private final UserService userService;
    private Key key;


    @PostConstruct // он вызвовится после конструктора
    public void init(){
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(Long userId, String userName, Set<Role> roles){ // access token, который представляет из себя строку
        Claims claims = Jwts.claims().setSubject(userName); // Хранит инфу о пользователе
        claims.put("id",userId);
        claims.put("roles",resolveRoles(roles));
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // когда токен был создан
                .setExpiration(validity)
                .signWith(key)
                .compact();

    }

    private List<String> resolveRoles(Set<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList()); // мы спарсили Роли в String лист
    }


    public String createRefreshToken(Long userId,String userName){
        Claims claims = Jwts.claims().setSubject(userName); // claims - это информация, которая хранится в payload
        claims.put("id",userId);
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getRefresh()); // validity хранит данные сейчас + 30 дней
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // когда токен был создан
                .setExpiration(validity)
                .signWith(key)
                .compact();
    }


    public JwtResponse refreshUserTokens(String refreshToken){
        JwtResponse jwtResponse = new JwtResponse();
        if (!validatedToken(refreshToken)){
            throw new AccessDeniedException();
        }
        Long userId = Long.valueOf(getId(refreshToken));
        User user = userService.getById(userId);
        jwtResponse.setId(userId);
        jwtResponse.setUserName(user.getUserName());
        jwtResponse.setAccessToken(createAccessToken(userId, user.getUserName(),user.getRoles()));
        jwtResponse.setRefreshToken(createRefreshToken(userId,user.getUserName()));

        return jwtResponse;
    }


    public boolean validatedToken(String token){ // Мы проверяем дату сейчас с датой которая будет заканчиваться
            Jws<Claims> claimsJwts = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return !claimsJwts.getBody().getExpiration().before(new Date()); // он будет не будет заканчиваться раньше

    }

    public String getId(String token){ // мы будем здесь айди
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("id")
                .toString();
    }

    private String getUsername(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // отсюда получаем юзернейм
    }
    public Authentication getAuthentication(String token){
        String userName = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }
}
