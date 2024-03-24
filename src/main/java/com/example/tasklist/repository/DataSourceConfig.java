package com.example.tasklist.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/*
Тут будет открывать транзакции и получать подключение к базе через jdbc
 */
@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {
    private final DataSource dataSource;


    // это будет помогать Spring на уровне сервисов, из-за того, что есть там аннотации Transactional
    public Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

}
