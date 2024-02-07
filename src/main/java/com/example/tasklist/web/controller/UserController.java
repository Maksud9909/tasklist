package com.example.tasklist.web.controller;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.service.TaskService;
import com.example.tasklist.service.UserService;
import com.example.tasklist.web.dto.task.TaskDto;
import com.example.tasklist.web.dto.user.UserDto;
import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.example.tasklist.web.mappers.TaskMapper;
import com.example.tasklist.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor // из-за него не надо писать autowired
@Validated
public class UserController {
    private final UserService userService;
    private final TaskService taskService;

    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    /**
     *
     * @param userDto @RequestBody UserDto userDto: чтобы сделать этот метод мы должны внутри тела запроса отправить готового юзера
     *                @Validated(OnUpdate.class): Группа валидации для обновления.
     * @return
     */
    @PutMapping
    public UserDto update(@Validated(OnUpdate.class)@RequestBody UserDto userDto){
        User user = userMapper.userToEntity(userDto); // мы получаем из тела запроса метода юзера
        User updatedUser = userService.update(user); // затем мы его апдейтим в базе данных
        return userMapper.toDto(updatedUser); // потом обратно в dto
    }


    /**
     *
     * @param id получаем айди из сылки
     * @return возвращаем этого работника, поменяв его в dto
     */
    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id){
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        userService.delete(id);
    }

    @GetMapping("/{id}/tasks") // через юзера получаем все таски
    public List<TaskDto> getTaskByUserId(@PathVariable Long id){
        List<Task> taskList = taskService.getAllByUserId(id); // получаем все задания через getAllByUserId
        return taskMapper.toDto(taskList);
    }

    @PostMapping("/{id}/tasks")
    public TaskDto createTask(@PathVariable Long id, @Validated(OnCreate.class) @RequestBody TaskDto dto) {
        Task task = taskMapper.taskToEntity(dto);
        Task createdTask = taskService.create(task,id);
        return taskMapper.toDto(createdTask);
    }


}
















