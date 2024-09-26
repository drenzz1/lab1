package org.foo.services.impl;

import org.foo.dto.CompanyDto;
import org.foo.dto.UserDto;
import org.foo.enums.CompanyStatus;
import org.foo.mapper.CompanyDtoMapper;
import org.foo.models.Company;
import org.foo.repository.CompanyRepository;
import org.foo.services.CompanyService;
import org.foo.services.SecurityService;
import org.foo.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
  private final CompanyDtoMapper companyDtoMapper;
  private final CompanyRepository companyRepository;
  private final UserService userService;
  private final SecurityService securityService;

  public CompanyServiceImpl(CompanyDtoMapper companyDtoMapper, CompanyRepository companyRepository, UserService userService, SecurityService securityService) {
    this.companyDtoMapper = companyDtoMapper;
    this.companyRepository = companyRepository;
    this.userService = userService;
    this.securityService = securityService;
  }

  @Override
  public List<CompanyDto> getAllCompanies() {
    return companyRepository.findAll().stream().map(companyDtoMapper).toList();

  }

  @Override
  public void save(CompanyDto company) {
    Company company1 = new Company(company.title(),company.phone(),company.website(),
      company.addressLine1(),company.addressLine2(),company.city(),company.state(),company.country(),company.zipCode(),company.companyStatus());

    companyRepository.save(company1);
  }

  @Override
  public void activateCompany(Long id) {
    Company company = companyRepository.findById(id).get();
    company.setCompanyStatus(CompanyStatus.ACTIVE);

    companyRepository.save(company);
  }

  @Override
  public void deactivateCompany(Long id) {
    Company company = companyRepository.findById(id).get();
    company.setCompanyStatus(CompanyStatus.PASSIVE);
    companyRepository.save(company);
  }



  @Override
  public CompanyDto findCompanyById(Long companyId) {
    return companyDtoMapper.apply(companyRepository.findById(companyId).get());
  }

  @Override
  public CompanyDto update(Long companyId, CompanyDto companyDto) {

    companyRepository.findById(companyId).ifPresent(company -> {
      company.setTitle(companyDto.title());
      company.setCompanyStatus(companyDto.companyStatus());
      company.setPhone(companyDto.phone());
      company.setWebsite(companyDto.website());
      company.setAddressLine1(companyDto.addressLine1());
      company.setAddressLine2(companyDto.addressLine2());
      company.setCity(companyDto.city());
      company.setState(companyDto.state());
      company.setCountry(companyDto.country());
      company.setZipCode(companyDto.zipCode());
    });
    return null;
  }

  @Override
  public boolean isTitleExist(String title) {
    return companyRepository.existsByTitle(title);
  }

  @Override
  public CompanyDto getCompanyDtoByLoggedInUser() {
    return null;
  }
}
