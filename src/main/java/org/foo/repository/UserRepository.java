package org.foo.repository;

import jakarta.transaction.Transactional;
import org.foo.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.foo.models.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);

  @Transactional
  void deleteByUsername(String username);

  List<User> findAllByRoleDescriptionIgnoreCase(String description);

  List<User> findAllByCompany(Company actualCompany);

}
