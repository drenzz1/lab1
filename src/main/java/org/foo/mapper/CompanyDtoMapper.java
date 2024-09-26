package org.foo.mapper;

import org.foo.dto.CompanyDto;
import org.foo.enums.CompanyStatus;
import org.foo.models.Company;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class CompanyDtoMapper implements Function<Company, CompanyDto> {

  public CompanyDtoMapper(AddressDtoMapper addressDtoMapper) {
  }

  @Override
  public CompanyDto apply(Company company) {
    return new CompanyDto(company.getId(),
      company.getTitle(),
      company.getPhone(),
      company.getWebsite(),
      company.getAddressLine1(),
      company.getAddressLine2(),
      company.getCity(),
      company.getState(),
      company.getCountry(),
      company.getZipCode(),
      company.getCompanyStatus());
  }
}
