package org.foo.services.impl;

import org.foo.dto.UserDto;
import org.foo.mapper.UserDtoMapper;
import org.foo.models.User;
import org.foo.models.common.UserPrincipal;
import org.foo.repository.UserRepository;
import org.foo.services.SecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService, SecurityService {

  private final UserRepository userRepository;
  private final UserDtoMapper userDtoMapper;

  public CustomerUserDetailsService(UserRepository userRepository, UserDtoMapper userDtoMapper) {
    this.userRepository = userRepository;
    this.userDtoMapper = userDtoMapper;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUserName(username).get();
    if (user ==null){
      throw new UsernameNotFoundException("This user does not exists");
    }
    UserPrincipal userPrincipal=new UserPrincipal(user);
    return userPrincipal;

  }

  @Override
  public UserDto getLoggedInUser() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    return userDtoMapper.apply(userRepository.findByUserName(username).get());
  }
}
