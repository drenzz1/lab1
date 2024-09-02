package org.foo.repository;

import org.foo.models.Category;
import org.foo.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  List<Category> findAllByCompany(Company company);

  Category findByDescriptionAndCompany(String description, Company actualCompany);
}
