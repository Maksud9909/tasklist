package com.example.tasklist.domain.task;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Task {
    private Long id;

    private String title;

    private String description;

    private Status status; // status is done, not or in the process

    private LocalDateTime expirationDate; // when you have to end this task
}
