package com.example.tasklist.domain.user;

import com.example.tasklist.domain.task.Task;
import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;
    @Transient
    private String confirmationOfPassword;

    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER) // так как это роли
    @CollectionTable(name = "users_roles") // отдельная таблица для листов
    @Enumerated(value = EnumType.STRING) // тип роли
    private Set<Role> roles;
    @CollectionTable(name = "users_tasks")
    @OneToMany
    @Column(name = "task_id")
    private List<Task>tasks;
}
