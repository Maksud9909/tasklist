package com.example.tasklist.config;

import com.example.tasklist.web.security.JwtTokenFilter;
import com.example.tasklist.web.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигурация безопасности приложения.
 * Этот класс определяет настройки аутентификации и авторизации,
 * включая управление сессиями, обработку исключений,
 * правила авторизации для различных URL-путей,
 * а также фильтры безопасности.
 */
@Configuration // место где собираются все бины
@EnableWebSecurity
@RequiredArgsConstructor // сразу пишет конструктор для всех переменных
public class ApplicationConfig {

    private final JwtTokenProvider tokenProvider;

    private final ApplicationContext applicationContext;

    /**
     * Шифрование паролей
     * @return Возвращает объект для шифрования паролей
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Этот бин предоставляет AuthenticationManager для управления аутентификацией в приложении.
     * @param configuration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    /**
     * Предоставляет цепочку фильтров безопасности для обработки HTTP-запросов.
     *
     * <p>Этот бин определяет настройки безопасности, такие как отключение CSRF, обработка CORS,
     * настройки аутентификации и авторизации, управление сессиями,
     * обработка исключений в случае неудачной аутентификации или доступа, а также правила авторизации.
     * Также добавляется фильтр для обработки JWT-токенов перед стандартным фильтром аутентификации по имени пользователя и паролю.
     * </p>
     *
     * @param httpSecurity Объект конфигурации безопасности HTTP.
     * @return SecurityFilterChain для применения настроек безопасности к HTTP-запросам.
     * @throws Exception В случае возникновения ошибки при настройке фильтров безопасности.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable() //Отключает CSRF (Cross-Site Request Forgery)
                .cors() // Включает поддержку Cross-Origin Resource Sharing
                .and()
                .httpBasic()
                .disable()// Отключает HTTP Basic аутентификацию
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // указывает, что не нужно создавать сессии, так как аутентификация будет выполняться с использованием токенов
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) ->{
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().write("Unouthorized");
                })) // Настраивает обработку исключений, связанных с аутентификацией и доступом
                .accessDeniedHandler(((request, response, accessDeniedException) -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Unouthorized");
                }))
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .anyRequest().authenticated() // Задает правила авторизации для различных URL-путей
                .and()
                .anonymous() // Отключает анонимную аутентификацию.
                .disable()
                .addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build(); // фильтр будет обрабатывать токены JWT, выполнять аутентификацию и устанавливать контекст
    }
}
