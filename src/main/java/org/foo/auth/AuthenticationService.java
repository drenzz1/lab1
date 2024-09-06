package org.foo.auth;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final CustomerDTOMapper customerDTOMapper;
  private final JWTUtil jwtUtil;
  private final UserDetailsService userDetailsService;


  public AuthenticationService(AuthenticationManager authenticationManager, CustomerDTOMapper customerDTOMapper, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
    this.authenticationManager = authenticationManager;
    this.customerDTOMapper = customerDTOMapper;
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  public AuthenticationResponse login(AuthenticationRequest request) {
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.username(),
        request.password()
      )
    );
    Customer principal = (Customer) authentication.getPrincipal();
    CustomerDTO customerDTO = customerDTOMapper.apply(principal);
    String token = jwtUtil.issueToken(customerDTO.username(), customerDTO.roles());
    var refreshToken = jwtUtil.generateRefreshToken(customerDTO.username());
    return new AuthenticationResponse(token, refreshToken,customerDTO);
  }

  public void refreshToken(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtUtil.getSubject(refreshToken);
    if (userEmail != null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

      if (jwtUtil.isTokenValid(refreshToken, userDetails.getUsername())) {
        var accessToken = jwtUtil.issueToken(userDetails.getUsername());
        var authResponse = AuthenticationResponse.builder()
          .accesstoken(accessToken)
          .refreshToken(refreshToken)
          .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
