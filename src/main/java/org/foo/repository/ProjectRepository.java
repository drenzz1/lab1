package org.foo.repository;

import org.foo.models.Project;
import org.foo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
  Project findByProjectCode(String code);
  List<Project> findAllByAssignedManager(User manager);
}
