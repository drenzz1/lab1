package org.foo.mapper;

import org.foo.dto.RoleDto;
import org.foo.models.Role;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RoleDtoMapper implements Function<Role, RoleDto> {
  @Override
  public RoleDto apply(Role role) {
    return new RoleDto(role.getId(),
      role.getDescription());
  }
}
