package org.foo.repository;


import org.foo.dto.ProductDto;
import org.foo.models.Company;
import org.foo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  List<Product> findAllByCategoryCompany(Company company);

  List<Product> findByCategoryId(Long categoryId);

  boolean existsByName(String name);

  Product findByNameAndCategoryCompany(String name, Company actualCompany);

  List<Product> findProductsByCategory_CompanyId(Long companyId);

  Optional<Object> findByNameAndInsertUserId(String name, Long companyId);
}
