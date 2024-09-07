package org.foo.mapper;

import org.foo.dto.CompanyDto;
import org.foo.enums.CompanyStatus;
import org.foo.models.Company;
import org.springframework.stereotype.Component;

import java.util.function.Function;
@Component
public class CompanyDtoMapper implements Function<Company, CompanyDto> {
  private final AddressDtoMapper addressDtoMapper;

  public CompanyDtoMapper(AddressDtoMapper addressDtoMapper) {
    this.addressDtoMapper = addressDtoMapper;
  }

  @Override
  public CompanyDto apply(Company company) {
    return new CompanyDto(company.getId(),
      company.getTitle(),
      company.getPhone(),
      company.getWebsite(),
      addressDtoMapper.apply(company.getAddress()),
      company.getCompanyStatus());
  }
}
