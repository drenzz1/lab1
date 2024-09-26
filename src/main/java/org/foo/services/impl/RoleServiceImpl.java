package org.foo.services.impl;

import org.foo.dto.RoleDto;
import org.foo.mapper.RoleDtoMapper;
import org.foo.repository.RoleRepository;
import org.foo.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

  private final RoleRepository roleRepository;
  private final RoleDtoMapper roleDtoMapper;

  public RoleServiceImpl(RoleRepository roleRepository, RoleDtoMapper roleDtoMapper) {
    this.roleRepository = roleRepository;
    this.roleDtoMapper = roleDtoMapper;
  }

  @Override
  public List<RoleDto> listAllRoles() {
    return roleRepository.findAll().stream().map(roleDtoMapper).toList();
  }

  @Override
  public RoleDto findById(Long id) {
    return roleDtoMapper.apply(roleRepository.findRoleById(id));
  }
}
