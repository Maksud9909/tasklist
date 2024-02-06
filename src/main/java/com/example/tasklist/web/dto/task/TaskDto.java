package com.example.tasklist.web.dto.task;

import com.example.tasklist.domain.task.Status;
import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
@Data
public class TaskDto {
    @NotNull(message = "Id must be not null.",groups = OnUpdate.class) // потом в контроллере будем проверять groups
    private Long id;

    @NotNull(message = "Title must be not null", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255,message = "Title must be less than 255")
    private String title;

    private String description;

    private Status status; // status is done or not

    private LocalDateTime expirationDate; // when you have to end this task
}
