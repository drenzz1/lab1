package org.foo.dto;

import org.foo.enums.CompanyStatus;

public record CompanyDto(Long id,
                         String title,
                         String phone,
                         String website,
                         AddressDto addressDto,
                         CompanyStatus companyStatus) {
}
