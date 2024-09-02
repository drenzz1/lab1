package org.foo.repository;

import org.foo.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

  Company findCompanyByTitle(String title);

  boolean existsByTitle(String title);

}
