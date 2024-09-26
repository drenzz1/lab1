package org.foo.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.foo.enums.Status;
import org.hibernate.annotations.Where;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@Table(name = "tasks")
@NoArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class Task extends BaseEntity {
  private String taskSubject;
  private String taskDetail;
  @Enumerated(value = EnumType.STRING)
  private Status taskStatus;
  @Column(columnDefinition = "DATE")
  private LocalDate assignedDate;
  @ManyToOne(fetch = FetchType.LAZY)
  private User assignedEmployee;
  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;
}
