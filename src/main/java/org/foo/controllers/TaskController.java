package org.foo.controllers;

import org.foo.dto.TaskDto;
import org.foo.enums.Status;
import org.foo.services.ProjectService;
import org.foo.services.TaskService;
import org.foo.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {
  private final TaskService taskService;



  public TaskController(TaskService taskService) {
    this.taskService = taskService;


  }

  @GetMapping("/all-tasks")
  public List<TaskDto> getAllTasks() {
    return taskService.listAllTasks();
  }

  @GetMapping("{id}")
  public TaskDto getTaskById(@PathVariable Long id) {
    return taskService.findById(id);
  }

  @PostMapping("/create")
  public void createTask(@RequestBody TaskDto task) {
    taskService.save(task);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteTask(@PathVariable Long id) {
    taskService.delete(id);
  }
  @PutMapping("/edit/{id}")
  public void editTask(@PathVariable Long id, @RequestBody TaskDto task) {
    taskService.update(id,task);
  }

  @GetMapping("/not-completed")
  public List<TaskDto> getNotCompletedTasks() {
    return taskService.listAllTasksByStatusIsNot(Status.COMPLETE);
  }
  @GetMapping("/completed")
  public List<TaskDto> getCompletedTasks() {
    return taskService.findAllTasksByStatus(Status.COMPLETE);
  }


}
