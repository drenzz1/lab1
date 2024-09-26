package org.foo.mapper;

import org.foo.dto.UserDto;
import org.foo.models.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDtoMapper implements Function<User, UserDto> {
  private final RoleDtoMapper roleDtoMapper;


  public UserDtoMapper(RoleDtoMapper roleDtoMapper) {
    this.roleDtoMapper = roleDtoMapper;

  }


  @Override
  public UserDto apply(User user) {
   return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getUserName(),user.getPassWord(),user.getPhone(),roleDtoMapper.apply(user.getRole()),user.getGender());
  }
}
