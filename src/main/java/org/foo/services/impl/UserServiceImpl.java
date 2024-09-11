package org.foo.services.impl;

import org.foo.dto.UserDto;
import org.foo.mapper.CompanyDtoMapper;
import org.foo.mapper.UserDtoMapper;
import org.foo.models.Company;
import org.foo.models.Role;
import org.foo.models.User;
import org.foo.repository.CompanyRepository;
import org.foo.repository.RoleRepository;
import org.foo.repository.UserRepository;
import org.foo.services.SecurityService;
import org.foo.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserDtoMapper userDtoMapper;
  private final RoleRepository roleRepository;
  private final SecurityService securityService;
  private final CompanyRepository companyRepository;


  public UserServiceImpl(UserRepository userRepository, UserDtoMapper userDtoMapper, RoleRepository roleRepository, SecurityService securityService, CompanyRepository companyRepository) {
    this.userRepository = userRepository;
    this.userDtoMapper = userDtoMapper;
    this.roleRepository = roleRepository;
    this.securityService = securityService;
    this.companyRepository = companyRepository;
  }

  @Override
  public List<UserDto> listAllUsers() {
    UserDto loggedInUser =securityService.getLoggedInUser();

    if (loggedInUser.roleDto().description().contains("Root")){
      return userRepository.findAll()
        .stream().filter(user -> user.getRole().getDescription().contains("Admin"))
        .map(userDtoMapper)
        .toList();

    }else{
      Company company =  companyRepository.findById(securityService.getLoggedInUser().companyDto().id()).get();

      return userRepository.findAllByCompany(company).stream().filter(user -> !user.getIsDeleted())
        .map(userDtoMapper).toList();
    }
  }

  @Override
  public UserDto findByUsername(String username) {
    return userDtoMapper.apply(userRepository.findByUsername(username));
  }

  @Override
  public boolean checkForAdminRole(String username) {
    User user = userRepository.findByUsername(username);

    return user!= null && user.getRole()!=null && user.getRole().getDescription().equals("Admin");
  }

  @Override
  public void save(UserDto dto) {
    Role roleRepository1 = roleRepository.findById(dto.roleDto().id()).get();
    Company company = companyRepository.findById(dto.companyDto().id()).get();


    User user = new User(dto.username(), dto.password(),dto.firstName(), dto.lastName(), dto.phone(),roleRepository1,company);

    userRepository.save(user);

  }

  @Override
  public void update(Long id ,UserDto dto) {
    Role roleRepository1 = roleRepository.findById(dto.roleDto().id()).get();
    Company company = companyRepository.findById(dto.companyDto().id()).get();

    userRepository.findById(id).ifPresent(user -> {
      user.setRole(roleRepository1);
      user.setPhone(dto.phone());
      user.setFirstname(dto.firstName());
      user.setPassword(dto.password());
      user.setCompany(company);


      userRepository.save(user);
    });


  }

  @Override
  public void deleteByUserName(String username) {

  }

  @Override
  public void delete(String username) {
    User user = userRepository.findByUsername(username);
    user.setIsDeleted(true);
    userRepository.save(user);
  }

  @Override
  public List<UserDto> listAllByRole(String role) {
    return userRepository.findAllByRoleDescriptionIgnoreCase(role).stream().map(userDtoMapper).toList();
  }

  @Override
  public void deleteById(Long id) {
    User user = userRepository.findById(id).get();
    user.setIsDeleted(true);
    userRepository.save(user);

  }

  @Override
  public UserDto findById(Long id) {
   return userDtoMapper.apply( userRepository.findById(id).get());
  }
}
