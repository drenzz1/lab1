package org.foo.controllers;

import org.foo.dto.ProjectDto;
import org.foo.services.ProjectService;
import org.foo.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
  private final ProjectService projectService;


  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;

  }

  @GetMapping("/list-all")
  public List<ProjectDto> getAllProjects() {
    return projectService.listAllProjects();
  }
  @GetMapping("/list-all/manager")
  public List<ProjectDto> getAllProjects1() {
    return projectService.listAllProjectDetails();
  }
  @PostMapping
  public void createProject(@RequestBody ProjectDto projectDto) {
    projectService.save(projectDto);
  }
  @PutMapping("/edit/{id}")
  public void editProject(@PathVariable Long id, @RequestBody ProjectDto projectDto) {
    projectService.update(id,projectDto);
  }
  @DeleteMapping("/{id}")
  public void deleteProject(@PathVariable Long id) {
    projectService.delete(id);
  }
  @GetMapping("/complete/{id}")
  public void completeProject(@PathVariable Long id) {
    projectService.complete(id);
  }


}
