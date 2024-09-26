package org.foo.mapper;

import org.foo.dto.TaskDto;
import org.foo.models.Task;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class TaskDtoMapper implements Function<Task, TaskDto> {
  private final ProjectDtoMapper projectDtoMapper;
  private final UserDtoMapper userDtoMapper;

  public TaskDtoMapper(ProjectDtoMapper projectDtoMapper, UserDtoMapper userDtoMapper) {
    this.projectDtoMapper = projectDtoMapper;
    this.userDtoMapper = userDtoMapper;
  }

  @Override
  public TaskDto apply(Task task) {
    return new TaskDto(task.getId(),projectDtoMapper.apply(task.getProject()),userDtoMapper.apply(task.getAssignedEmployee()),task.getTaskSubject(),task.getTaskDetail(),task.getTaskStatus(),task.getAssignedDate());
  }
}
