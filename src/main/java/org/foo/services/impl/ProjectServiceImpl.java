package org.foo.services.impl;

import org.foo.dto.ProjectDto;
import org.foo.enums.Status;
import org.foo.mapper.ProjectDtoMapper;
import org.foo.mapper.UserDtoMapper;
import org.foo.models.Project;
import org.foo.models.User;
import org.foo.repository.ProjectRepository;
import org.foo.repository.UserRepository;
import org.foo.services.ProjectService;
import org.foo.services.TaskService;
import org.foo.services.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;
  private final ProjectDtoMapper projectMapper;
  private final UserService userService;
  private final UserDtoMapper userMapper;
  private final TaskService taskService;
  private final UserRepository userRepository;


  public ProjectServiceImpl(ProjectRepository projectRepository, ProjectDtoMapper projectMapper, @Lazy UserService userService, UserDtoMapper userMapper, TaskService taskService, UserRepository userRepository) {
    this.projectRepository = projectRepository;
    this.projectMapper = projectMapper;
    this.userService = userService;
    this.userMapper = userMapper;
    this.taskService = taskService;
    this.userRepository = userRepository;
  }

  @Override
  public ProjectDto getByProjectCode(String code) {
    return  projectMapper.apply(projectRepository.findByProjectCode(code));
  }


  @Override
  public void save(ProjectDto projectDTO) {

    projectDTO.setProjectStatus(Status.OPEN);
    Project project = new Project(projectDTO.getProjectCode(),projectDTO.getProjectName(),userRepository.findById(projectDTO.getAssignedManager().getId()).get(),projectDTO.getStartDate(),projectDTO.getEndDate(),projectDTO.getProjectDetail(),projectDTO.getProjectStatus());
    projectRepository.save(project);
  }

  @Override
  public void update(Long id , ProjectDto projectDTO) {
    Project project = projectRepository.findByProjectCode(projectDTO.getProjectCode());


    projectRepository.findById(id).ifPresent(project1 -> {
      project1.setProjectName(projectDTO.getProjectName());
      project1.setStartDate(projectDTO.getStartDate());
      project1.setEndDate(projectDTO.getEndDate());
      project1.setProjectDetail(projectDTO.getProjectDetail());
      project1.setProjectStatus(projectDTO.getProjectStatus());
      project1.setProjectStatus(Status.OPEN);

      projectRepository.save(project1);
    });
  }

  @Override
  public void delete(Long id) {
    Project project = projectRepository.findById(id).get();
    project.setIsDeleted(true);
    project.setProjectCode(project.getProjectCode()+"-"+project.getId());

    projectRepository.save(project);

    taskService.deleteByProject(projectMapper.apply(project));
  }

  @Override
  public void complete(Long projectCode) {
    Project project = projectRepository.findById(projectCode).get();
    project.setProjectStatus(Status.COMPLETE);
    projectRepository.save(project);
    taskService.completeByProject(projectMapper.apply(project));
  }

  @Override
  public List<ProjectDto> listAllProjectDetails() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    User user = userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).get();

    List<Project>list =  projectRepository.findAllByAssignedManager(user);
    return  list.stream().map(project -> {

        ProjectDto obj=projectMapper.apply(project);
        obj.setUnfinishedTaskCounts(taskService.totalNonCompletedTask(project.getProjectCode()));
        obj.setCompleteTaskCounts(taskService.totalCompletedTask(project.getProjectCode()));
        return obj;

      }
    ).collect(Collectors.toList());

  }
  @Override
  public List<ProjectDto> listAllProjects() {
    return projectRepository.findAll().stream().map(project -> {
      ProjectDto obj=projectMapper.apply(project);
      obj.setUnfinishedTaskCounts(taskService.totalNonCompletedTask(project.getProjectCode()));
      obj.setCompleteTaskCounts(taskService.totalCompletedTask(project.getProjectCode()));
      return obj;
    }).collect(Collectors.toList());
  }


  @Override
  public List<ProjectDto> readAllByAssignedManager(User assignedManager) {
    List<Project> list = projectRepository.findAllByAssignedManager(assignedManager);
    return list.stream().map(projectMapper).collect(Collectors.toList());
  }

  @Override
  public ProjectDto findById(Long id) {
   return  projectMapper.apply(projectRepository.findById(id).get());

  }
}
