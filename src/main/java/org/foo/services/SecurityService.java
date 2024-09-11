package org.foo.services;

import org.foo.dto.UserDto;

public interface SecurityService {
  UserDto getLoggedInUser();

}
