package com.example.tasklist.web.dto.user;

import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
public class UserDto {
    @NotNull(message = "Id must be not null.",groups = OnUpdate.class) // потом в контроллере будем проверять groups
    private Long id;

    // аннотация, говорит, что объект не должен быть null, а groups говорит, когда он не должен быть null
    @NotNull(message = "Name must be not null.", groups = {OnUpdate.class, OnCreate.class}) // теперь этот объект не будет null
    @Length(max = 255,message = "Name must be less than 255 symbols",groups = {OnCreate.class, OnUpdate.class})
    private String name;


    // аннотация, говорит, что объект не должен быть null, а groups говорит, когда он не должен быть null
    @NotNull(message = "Name must be not null.", groups = {OnUpdate.class, OnCreate.class}) // теперь этот объект не будет null
    @Length(max = 255,message = "Name must be less than 255 symbols",groups = {OnCreate.class, OnUpdate.class})
    private String userName;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // мы только можем писать пароль, а смотреть его не можем, когда будем получать json
    @NotNull(message = "Password must be not null",groups = {OnUpdate.class, OnCreate.class})
    private String password;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // мы только можем писать пароль, а смотреть его не можем
    @NotNull(message = "Confirmation of password must be not null",groups = OnUpdate.class)
    private String confirmationOfPassword; // passwordConfirmationByEmail
}
