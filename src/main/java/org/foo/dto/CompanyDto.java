package org.foo.dto;

import org.foo.enums.CompanyStatus;
import org.foo.models.Address;

public record CompanyDto(Long id,
                         String title,
                         String phone,
                         String website,
                         Address addressDto,
                         CompanyStatus companyStatus) {
}
