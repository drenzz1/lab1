package org.foo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.foo.enums.Status;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class TaskDto {

  private Long id;

  private ProjectDto project;

  private UserDto assignedEmployee;

  private String taskSubject;

  private String taskDetail;

  private Status taskStatus;
  private LocalDate assignedDate;



}
