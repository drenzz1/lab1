package org.foo.dto;

import java.util.List;

public record UserDto(Long id ,
                      String username ,
                      String password,
                      String lastName,
                      String phone,
                      RoleDto roleDto,
                      CompanyDto companyDto
                      ) {
}
