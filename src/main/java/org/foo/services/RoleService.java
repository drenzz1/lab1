package org.foo.services;

import org.foo.dto.RoleDto;

import java.util.List;

public interface RoleService {
  List<RoleDto> listAllRoles();
  RoleDto findById(Long id);
}
