package org.foo.mapper;

import org.foo.dto.ProjectDto;
import org.foo.models.Project;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProjectDtoMapper implements Function<Project, ProjectDto> {
  private final UserDtoMapper userDtoMapper;

  public ProjectDtoMapper(UserDtoMapper userDtoMapper) {
    this.userDtoMapper = userDtoMapper;
  }

  @Override
  public ProjectDto apply(Project project) {
    return new ProjectDto(project.getId(),project.getProjectName(), project.getProjectCode(),userDtoMapper.apply(project.getAssignedManager()),project.getStartDate(),project.getEndDate(),project.getProjectDetail(),project.getProjectStatus(),0,0);
  }
}
