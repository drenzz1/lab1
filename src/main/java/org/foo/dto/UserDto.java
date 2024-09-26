package org.foo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.foo.enums.Gender;


@Setter
@Getter
@AllArgsConstructor
public class  UserDto {


  private Long id;


  private String firstName;


  private String lastName;


  private String userName;

  private String passWord;

  private String phone;


  private RoleDto role;


  private Gender gender;




}
