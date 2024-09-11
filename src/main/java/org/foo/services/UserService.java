package org.foo.services;

import org.foo.dto.UserDto;

import java.util.List;

public interface UserService {
  List<UserDto> listAllUsers();
  UserDto findByUsername(String username);

  boolean checkForAdminRole(String username);

  void save(UserDto dto);
  void update(Long id ,UserDto dto);
  void deleteByUserName(String username);
  void delete(String username);
  List<UserDto> listAllByRole(String role);

  void deleteById(Long id);

  UserDto findById(Long id);
}
