package org.foo.mapper;

import org.foo.dto.UserDto;
import org.foo.models.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDtoMapper implements Function<User, UserDto> {
  @Override
  public UserDto apply(User user) {
    return new UserDto(user.getId(),
      user.getUsername(),
      user.getPassword(),
      user.getLastname(),
      user.getPhone(),
      user.getRole(),user.getCompany()
      );
  }
}
