package com.example.tasklist.web.dto.user;

import com.example.tasklist.web.dto.validation.OnUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class UserDto {
    @NotNull(message = "Id must be not null.",groups = OnUpdate.class) // потом в контроллере будем проверять groups
    private Long id;

    private String name;

    private String userName;

    private String password;

    private String confirmationOfPassword; // passwordConfirmationByEmail


}
