package com.example.tasklist.domain.task;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Task implements Serializable {
    private Long id;

    private String title;

    private String description;

    private Status status;

    @Getter
    private LocalDateTime expirationDate;

}
