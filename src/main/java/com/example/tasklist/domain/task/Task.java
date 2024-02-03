package com.example.tasklist.domain.task;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Task {
    private long id;

    private String title;

    private String description;

    private Status status; // status is done or not

    private LocalDateTime expirationDate; // when you have to end this task
}
