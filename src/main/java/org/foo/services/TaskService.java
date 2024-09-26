package org.foo.services;

import org.foo.dto.ProjectDto;
import org.foo.dto.TaskDto;
import org.foo.enums.Status;
import org.foo.models.User;

import java.util.List;

public interface TaskService {
  TaskDto findById(Long id);
  List<TaskDto> listAllTasks();
  void save(TaskDto taskDTO);
  void update(Long id,TaskDto taskDTO);
  void delete(Long id);
  int totalNonCompletedTask(String projectCode);
  int totalCompletedTask(String projectCode);

  void deleteByProject(ProjectDto projectDTO);

  void completeByProject(ProjectDto projectDTO);

  List<TaskDto> listAllTasksByStatusIsNot(Status status);



  List<TaskDto> findAllTasksByStatus(Status status);

  List<TaskDto> readAllByAssignedEmployee(User assignedEmployee);

  void updateStatus(Long id, String status);
}
