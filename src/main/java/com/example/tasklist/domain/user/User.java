package com.example.tasklist.domain.user;

import com.example.tasklist.domain.task.Task;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data // она за нас пишет getter,setter,toString,equals,hashcode,constructor
public class User {

    private Long id;

    private String name;

    private String username;

    private String password;

    private String confirmationOfPassword; // passwordConfirmationByEmail

    private Set<Role> roles; // Roles like Admin or User

    private List<Task>tasks; // list of tasks

}
