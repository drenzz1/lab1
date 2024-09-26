package org.foo.services.impl;

import org.foo.dto.ProjectDto;
import org.foo.dto.TaskDto;
import org.foo.dto.UserDto;
import org.foo.mapper.UserDtoMapper;
import org.foo.models.Role;
import org.foo.models.User;
import org.foo.repository.RoleRepository;
import org.foo.repository.UserRepository;
import org.foo.services.ProjectService;
import org.foo.services.TaskService;
import org.foo.services.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserDtoMapper userDtoMapper;
  private final RoleRepository roleRepository;
  private final ProjectService projectService;
  private final TaskService taskService;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, UserDtoMapper userDtoMapper, RoleRepository roleRepository,@Lazy ProjectService projectService, @Lazy TaskService taskService, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.userDtoMapper = userDtoMapper;
    this.roleRepository = roleRepository;
    this.projectService = projectService;
    this.taskService = taskService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public List<UserDto> listAllUsers() {
    return  userRepository.findAll().stream().map(userDtoMapper).toList();

  }

  @Override
  public UserDto findByUsername(String username) {
    return userDtoMapper.apply(userRepository.findByUserName(username).get());

  }

  @Override
  public void save(UserDto userDTO) {
    Role role = roleRepository.findRoleById(userDTO.getRole().id());

    User user = new User(userDTO.getFirstName(),userDTO.getLastName(),userDTO.getUserName() , passwordEncoder.encode(userDTO.getPassWord()),true,userDTO.getPhone(),role,userDTO.getGender());
    userRepository.save(user);
  }

  @Override
  public UserDto update(Long id , UserDto userDTO) {

    userRepository.findById(id).ifPresent(user1 -> {
      user1.setFirstName(userDTO.getFirstName());
      user1.setLastName(userDTO.getLastName());
      user1.setUserName(userDTO.getUserName());
      user1.setPassWord(passwordEncoder.encode(userDTO.getPassWord()));

      user1.setEnabled(true);
      user1.setPhone(userDTO.getPhone());
      user1.setGender(userDTO.getGender());
      userRepository.save(user1);
    });
    return userDtoMapper.apply( userRepository.findById(id).get());
  }

  @Override
  public void deleteByUsername(String username) {
    userRepository.deleteByUserName(username);

  }

  @Override
  public void delete(Long  username) {

    User user = userRepository.findById(username).get();
    if (checkIfUserCanBeDeleted(user)){
      user.setIsDeleted(true);
      user.setUserName(user.getUserName() + " -" + user.getId());
      userRepository.save(user);
    }

  }
  private boolean checkIfUserCanBeDeleted(User user ){
    return switch (user.getRole().getDescription()) {
      case "Manager" -> {
        List<ProjectDto> projectDTOList = projectService.readAllByAssignedManager(user);
        yield projectDTOList.isEmpty();
      }
      case "Employee" -> {
        List<TaskDto> taskDTOList = taskService.readAllByAssignedEmployee(user);
        yield taskDTOList.isEmpty();
      }
      default -> true;
    };
  }

  @Override
  public List<UserDto> listAllByRole(String role) {



    return userRepository.findByRoleDescription(role).stream().map(userDtoMapper).toList();


  }

  @Override
  public UserDto findById(long id) {
    return userDtoMapper.apply(userRepository.findById(id).get());
  }
}
