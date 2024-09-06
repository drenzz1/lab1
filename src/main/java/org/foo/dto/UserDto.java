package org.foo.dto;

public record UserDto(Long id ,
                      String username ,
                      String lastName,
                      String phone,
                      RoleDto roleDto,
                      CompanyDto companyDto
                      ) {
}
