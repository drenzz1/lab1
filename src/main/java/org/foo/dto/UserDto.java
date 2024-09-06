package org.foo.dto;

public record UserDto(Long id , String username , String password,String confirmPassword,String lastName,String phone,RoleDto roleDto,CompanyDto companyDto,boolean isOnlyAdmin) {
}
