package com.example.tasklist.domain.task;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
 // нужно указать, что наш класс сериализован, чтобы redis было легче.
@Data
public class Task implements Serializable {
    private Long id;

    private String title;

    private String description;

    private Status status; // status is done, not or in the process

    private LocalDateTime expirationDate; // when you have to end this task
}
