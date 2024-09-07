package org.foo.mapper;

import org.foo.dto.UserDto;
import org.foo.models.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDtoMapper implements Function<User, UserDto> {
  private final RoleDtoMapper roleDtoMapper;
  private final CompanyDtoMapper companyDtoMapper;

  public UserDtoMapper(RoleDtoMapper roleDtoMapper, CompanyDtoMapper companyDtoMapper) {
    this.roleDtoMapper = roleDtoMapper;
    this.companyDtoMapper = companyDtoMapper;
  }

  @Override
  public UserDto apply(User user) {
    return new UserDto(user.getId(),
      user.getUsername(),
      user.getPassword(),
      user.getLastname(),
      user.getPhone(),
      roleDtoMapper.apply(user.getRole()),
      companyDtoMapper.apply(user.getCompany())
      );
  }
}
