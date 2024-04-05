package com.example.tasklist.web.dto.user;

import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Data
@Schema(description = "User DTO")
public class UserDto {
    @Schema(description = "User id",example = "1")
    @NotNull(message = "Id must be not null.",groups = OnUpdate.class) // потом в контроллере будем проверять groups
    private Long id;


    // аннотация, говорит, что объект не должен быть null, а groups говорит, когда он не должен быть null
    @Schema(description = "User name",example = "Maksud Rustamov")
    @NotNull(message = "Name must be not null.", groups = {OnUpdate.class, OnCreate.class}) // теперь этот объект не будет null
    @Length(max = 255,message = "Name must be less than 255 symbols",groups = {OnCreate.class, OnUpdate.class})
    private String name;


    // аннотация, говорит, что объект не должен быть null, а groups говорит, когда он не должен быть null
    @Schema(description = "User email",example = "rmaksud822@mail.ru")
    @NotNull(message = "Name must be not null.", groups = {OnUpdate.class, OnCreate.class}) // теперь этот объект не будет null
    @Length(max = 255,message = "Name must be less than 255 symbols",groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @Schema(description = "User crypted password",example = "$2a$10$U4xZmhD6eVtcFXuX6m5btuhQ.15r90n6JlgHDpssYKBroI1zXReC2")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // мы только можем писать пароль, а смотреть его не можем, когда будем получать json
    @NotNull(message = "Password must be not null",groups = {OnUpdate.class, OnCreate.class})
    private String password;

    @Schema(description = "User confirmation of the password",example = "$2a$10$U4xZmhD6eVtcFXuX6m5btuhQ.15r90n6JlgHDpssYKBroI1zXReC2")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // мы только можем писать пароль, а смотреть его не можем
    @NotNull(message = "Confirmation of password must be not null",groups = OnUpdate.class)
    private String confirmationOfPassword; // passwordConfirmationByEmail
}
