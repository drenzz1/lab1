package org.foo.services;

import org.foo.dto.CompanyDto;

import java.util.List;

public interface CompanyService {
  List<CompanyDto> getAllCompanies();

  void save(CompanyDto company);

  void activateCompany(Long id);

  void deactivateCompany(Long id);


  CompanyDto findCompanyById(Long companyId);

  CompanyDto update(Long companyId, CompanyDto companyDto);

  boolean isTitleExist(String title);

  CompanyDto getCompanyDtoByLoggedInUser();
}
