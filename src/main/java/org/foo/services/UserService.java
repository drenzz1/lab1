package org.foo.services;

import org.foo.dto.UserDto;

import java.util.List;

public interface UserService {
  List<UserDto> listAllUsers();
  UserDto findByUsername(String username);
  void save(UserDto userDTO);
  UserDto update(Long id ,UserDto userDTO);
  void deleteByUsername(String username);
  void delete(Long  username);
  List<UserDto>listAllByRole(String role);

  UserDto findById(long id);
}
