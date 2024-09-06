package org.foo.dto;

public record CategoryDto(Long id,
                          String description,
                          CompanyDto companyDto
                          ) {
}
