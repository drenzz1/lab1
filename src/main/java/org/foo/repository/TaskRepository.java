package org.foo.repository;

import org.foo.enums.Status;
import org.foo.models.Project;
import org.foo.models.Task;
import org.foo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

  @Query("select count(t) from Task t where t.project.projectCode = ?1 and t.taskStatus<>'COMPLETE'")
  public int totalNonCompletedTasks(String projectCode);
  @Query(value = "SELECT COUNT(*) FROM tasks t JOIN projects p ON t.project_id = p.id WHERE p.project_code = ?1 AND t.task_status = 'COMPLETE'", nativeQuery = true)
  public int totalCompletedTasks(String projectCode);
  List<Task> findAllByProject(Project project);

  List<Task> findAllByTaskStatusIsNotAndAssignedEmployee(Status status, User loggedInUser);

  List<Task> findAllByTaskStatusAndAssignedEmployee(Status status, User loggedInUser);

  List<Task> findAllByAssignedEmployee(User assignedEmployee);

  boolean existsByAssignedEmployee_Id(Long id);

}
