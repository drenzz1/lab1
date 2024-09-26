package org.foo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.foo.enums.Status;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProjectDto {

  private Long id;

  private String projectName;

  private String projectCode;

  private UserDto assignedManager;

  private LocalDate startDate;

  private LocalDate endDate;

  private String projectDetail;

  private Status projectStatus;

  private int completeTaskCounts;
  private int unfinishedTaskCounts;



}
