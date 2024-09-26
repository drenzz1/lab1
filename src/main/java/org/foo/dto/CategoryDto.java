package org.foo.dto;

public record CategoryDto(Long id,
                          String description,
                          boolean hasProduct,
                          CompanyDto companyDto
                          ) {
}
