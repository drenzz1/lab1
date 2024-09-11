package org.foo.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.foo.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.foo.dto.UserDto;
import org.foo.jwt.JWTUtil;
import org.foo.mapper.UserDtoMapper;
import org.foo.models.User;
import org.foo.services.impl.CustomerUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final UserDtoMapper userDtoMapper;
  private final JWTUtil jwtUtil;
  private final CustomerUserDetailsService userDetailsService;
  private final UserRepository userRepository;

  public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserDtoMapper userDtoMapper, JWTUtil jwtUtil, CustomerUserDetailsService userDetailsService, UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.userDtoMapper = userDtoMapper;
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
    this.userRepository = userRepository;
  }

  @Override
  public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
    // Authenticate the user
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        authenticationRequest.username(),
        authenticationRequest.password()
      )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    if(authentication.isAuthenticated()){
      String userName = authenticationRequest.username();
      User principal = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userName));
      // Convert User to UserDto
      UserDto userDto = userDtoMapper.apply(principal);

      // Issue tokens
      String token = jwtUtil.issueToken(userDto.username(), userDto.roleDto().description());
      String refreshToken = jwtUtil.generateRefreshToken(userDto.username());

      // Return response
      return new AuthenticationResponse(token, refreshToken);
    } else {
      throw new RuntimeException("Unexpected principal type: " + authentication.getPrincipal().getClass().getName());
    }

    }




  @Override
  public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, java.io.IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userName;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return ;
    }

    refreshToken=authHeader.substring(7);
    userName=jwtUtil.getSubject(refreshToken);
    if (userName!=null){
      UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

      if (jwtUtil.isTokenValid(refreshToken,userDetails.getUsername())){
        var accessToken = jwtUtil.issueToken(userDetails.getUsername());
        var authResponse = AuthenticationResponse.builder().accessToken(accessToken)
          .refreshToken(refreshToken)
          .build();

        new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
      }
    }

  }
}
