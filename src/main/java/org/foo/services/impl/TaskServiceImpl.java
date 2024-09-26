package org.foo.services.impl;

import org.foo.dto.ProjectDto;
import org.foo.dto.TaskDto;
import org.foo.dto.UserDto;
import org.foo.enums.Status;
import org.foo.mapper.ProjectDtoMapper;
import org.foo.mapper.TaskDtoMapper;
import org.foo.mapper.UserDtoMapper;
import org.foo.models.Project;
import org.foo.models.Task;
import org.foo.models.User;
import org.foo.repository.ProjectRepository;
import org.foo.repository.TaskRepository;
import org.foo.repository.UserRepository;
import org.foo.services.TaskService;
import org.foo.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final TaskDtoMapper taskMapper;
  private final ProjectDtoMapper projectMapper;
  private final UserService userService;
  private final UserDtoMapper userMapper;
  private final UserRepository userRepository;
  private final ProjectRepository projectRepository;

  public TaskServiceImpl(TaskRepository taskRepository, TaskDtoMapper taskMapper, ProjectDtoMapper projectMapper, UserService userService, UserDtoMapper userMapper, UserRepository userRepository, ProjectRepository projectRepository) {
    this.taskRepository = taskRepository;
    this.taskMapper = taskMapper;
    this.projectMapper = projectMapper;
    this.userService = userService;
    this.userMapper = userMapper;
    this.userRepository = userRepository;
    this.projectRepository = projectRepository;
  }

  @Override
  public TaskDto findById(Long id) {
    return taskMapper.apply(taskRepository.findById(id).get());
  }

  @Override
  public List<TaskDto> listAllTasks() {
    return taskRepository.findAll().stream().map(taskMapper).toList();
  }

  @Override
  public void save(TaskDto taskDTO) {
    User user = userRepository.findById(taskDTO.getAssignedEmployee().getId()).get();
    Project project = projectRepository.findById(taskDTO.getProject().getId()).get();
    taskDTO.setTaskStatus(Status.OPEN);
    taskDTO.setAssignedDate(LocalDate.now());
    Task task = new Task(taskDTO.getTaskSubject(),taskDTO.getTaskDetail(),taskDTO.getTaskStatus(),taskDTO.getAssignedDate(),user,project);
    taskRepository.save(task);
  }

  @Override
  public void update(Long id ,TaskDto taskDTO) {
    Optional<Task> task = taskRepository.findById(id);


    if (task.isPresent()){
      task.get().setId(taskDTO.getId());
      task.get().setTaskStatus(taskDTO.getTaskStatus()== null? task.get().getTaskStatus(): taskDTO.getTaskStatus());
      task.get().setAssignedDate(task.get().getAssignedDate());
      taskRepository.save(task.get());
    }

  }

  @Override
  public void delete(Long id) {
    Optional<Task> task = taskRepository.findById(id);
    if (task.isPresent()){
      task.get().setIsDeleted(true);
      taskRepository.save(task.get());
    }

  }

  @Override
  public int totalNonCompletedTask(String projectCode) {
    return taskRepository.totalNonCompletedTasks(projectCode);
  }

  @Override
  public int totalCompletedTask(String projectCode) {
    return taskRepository.totalCompletedTasks(projectCode);
  }

  @Override
  public void deleteByProject(ProjectDto projectDTO) {
    List<TaskDto>list = listAllByProject(projectDTO);
    list.forEach(taskDTO -> delete(taskDTO.getId()));
  }

  @Override
  public void completeByProject(ProjectDto projectDTO) {
    List<TaskDto>list = listAllByProject(projectDTO);
    list.forEach(taskDTO -> {
      taskDTO.setTaskStatus(Status.COMPLETE);
      update(projectDTO.getId(),taskDTO);
    });
  }

  @Override
  public List<TaskDto> listAllTasksByStatusIsNot(Status status) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User loggedInUser = userRepository.findByUserName(username).get();
    List<Task>list = taskRepository.findAllByTaskStatusIsNotAndAssignedEmployee(status,loggedInUser);
    return list.stream().map(taskMapper).collect(Collectors.toList());
  }

  @Override
  public void updateStatus(TaskDto taskDTO) {
    Optional<Task> task=taskRepository.findById(taskDTO.getId());
    if (task.isPresent()){
      task.get().setTaskStatus(taskDTO.getTaskStatus());
      taskRepository.save(task.get());
    }
  }

  @Override
  public List<TaskDto> findAllTasksByStatus(Status status) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User loggedInUser = userRepository.findByUserName(username).get();
    List<Task>list = taskRepository.findAllByTaskStatusAndAssignedEmployee(status,loggedInUser);
    return list.stream().map(taskMapper).collect(Collectors.toList());
  }

  @Override
  public List<TaskDto> readAllByAssignedEmployee(User assignedEmployee) {
    List<Task>list = taskRepository.findAllByAssignedEmployee(assignedEmployee);
    return list.stream().map(taskMapper).collect(Collectors.toList());
  }

  private List<TaskDto>listAllByProject(ProjectDto projectDTO){

    List<Task>list = taskRepository.findAllByProject(projectRepository.findById(projectDTO.getId()).get());
    return list.stream().map(taskMapper).collect(Collectors.toList());
  }


}
