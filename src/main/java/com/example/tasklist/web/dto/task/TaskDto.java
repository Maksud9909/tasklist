package com.example.tasklist.web.dto.task;

import com.example.tasklist.domain.task.Status;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TaskDto {
    private Long id;

    private String title;

    private String description;

    private Status status; // status is done or not

    private LocalDateTime expirationDate; // when you have to end this task
}
