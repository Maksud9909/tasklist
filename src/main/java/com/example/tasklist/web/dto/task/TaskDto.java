package com.example.tasklist.web.dto.task;

import com.example.tasklist.domain.task.Status;
import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
@Data
public class TaskDto {
    @NotNull(message = "Id must be not null.",groups = OnUpdate.class) // потом в контроллере будем проверять groups
    private Long id;

    @NotNull(message = "Title must be not null", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255,message = "Title must be less than 255",groups = {OnCreate.class, OnUpdate.class})
    private String title;
    @Length(max = 255,message = "Description must be less than 255",groups = {OnCreate.class, OnUpdate.class})
    private String description;

    private Status status; // status is done or not


    /**
     * These annotations control the formatting of the expirationDate field when it is serialized or deserialized.
     * @DateTimeFormat explains in format of time, it means it gives you rules, how to measure it
     * @JsonFormat configures the JSON serialization format.
     */
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expirationDate; // when you have to end this task
}
