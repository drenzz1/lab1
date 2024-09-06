package org.foo.services;

import org.foo.models.User;
import org.foo.models.common.UserPrincipal;
import org.foo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomerUserDetailsService(UserRepository userRepository) {
    this.userRepository= userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username).get();
    if (user ==null){
      throw new UsernameNotFoundException("This user does not exists");
    }
    UserPrincipal userPrincipal=new UserPrincipal(user);
    return userPrincipal;

  }
}
