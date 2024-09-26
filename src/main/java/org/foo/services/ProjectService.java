package org.foo.services;

import org.foo.dto.ProjectDto;
import org.foo.models.User;

import java.util.List;

public interface ProjectService {
  ProjectDto getByProjectCode(String code);
  List<ProjectDto> listAllProjects();
  void save(ProjectDto projectDTO);
  void update(Long id , ProjectDto projectDTO);
  void delete(Long id);
  void complete(Long projectCode);
  List<ProjectDto>listAllProjectDetails();

  List<ProjectDto> readAllByAssignedManager(User assignedManager);
}
