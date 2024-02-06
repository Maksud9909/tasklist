package com.example.tasklist.web.mappers;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.web.dto.task.TaskDto;

import org.mapstruct.Mapper;

import java.util.List;
/**
 *  Эта аннотация говорит MapStruct, что нужно создать Spring компонент для этого маппера.
 * Это означает, что вы сможете использовать его, внедряя его как бин в другие классы с помощью @Autowired.
 */
@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task task); // он преобразовывает Task в TaskDTO

    List<TaskDto> toDto(List<Task> tasks); // он преобразовывает Task в TaskDTO

    Task taskToEntity(TaskDto taskDto); // тут из TaskDTO в Task
}
