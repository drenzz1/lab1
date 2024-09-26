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
@Table(name = "projects")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Where(clause = "is_deleted=false")
public class Project extends BaseEntity {

  @Column(unique = true)
  private String projectCode;

  private String projectName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "manager_id")
  private User assignedManager;

  @Column(columnDefinition = "DATE")
  private LocalDate startDate;

  @Column(columnDefinition = "DATE")
  private LocalDate endDate;

  private String projectDetail;

  @Enumerated(EnumType.STRING)
  private Status projectStatus;


}
