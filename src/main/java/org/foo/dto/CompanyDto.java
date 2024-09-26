package org.foo.dto;

import org.foo.enums.CompanyStatus;

public record CompanyDto(Long id,
                         String title,
                         String phone,
                         String website,
                         String addressLine1,
                         String addressLine2,
                         String city,
                         String state,
                         String country,
                         String zipCode,
                         CompanyStatus companyStatus) {
}
