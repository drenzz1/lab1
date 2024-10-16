package org.foo.repository;

import org.foo.enums.ClientVendorType;
import org.foo.models.ClientVendor;
import org.foo.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientVendorRepository extends JpaRepository<ClientVendor, Long> {

  List<ClientVendor> findAllByCompany(Company company);

  List<ClientVendor> findAllByCompanyAndClientVendorType(Company company, ClientVendorType clientVendorType);

  ClientVendor findClientVendorById(Long id);

  ClientVendor findByClientVendorNameAndCompany(String companyName, Company actualCompany);
}
