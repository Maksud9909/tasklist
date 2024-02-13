package com.example.tasklist.service.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "security.jwt") // по этому префиксу мы будем получать данные через app.yaml
public class JwtProperties {
    private String secret;
    private long access;
    private long refresh;
}
