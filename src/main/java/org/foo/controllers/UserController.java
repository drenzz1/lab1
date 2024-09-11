package org.foo.controllers;


import org.foo.dto.RoleDto;
import org.foo.dto.UserDto;
import org.foo.models.Role;
import org.foo.services.CompanyService;
import org.foo.services.RoleService;
import org.foo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {

private final UserService userService;
private final RoleService roleService;
private final CompanyService companyService;

  public UserController(UserService userService, RoleService roleService, CompanyService companyService) {
    this.userService = userService;
    this.roleService = roleService;
    this.companyService = companyService;
  }

  @GetMapping("/list")
  public List<UserDto> getAllUsers(){
    return userService.listAllUsers();
  }
  @PostMapping("/create")
  public ResponseEntity<String> createUser(@RequestBody UserDto userDto){
    userService.save(userDto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
  @GetMapping("/role")
  public List<RoleDto> listAllRoles(){
    return roleService.listAllRoles();
  }
  @PutMapping("/edit/{id}")
  public ResponseEntity<String> editUser(@PathVariable("id")Long id , UserDto userDto){
    userService.update(id,userDto);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable("id")Long id){
    userService.deleteById(id);
    return ResponseEntity.status(HttpStatus.OK).build();

  }
}
