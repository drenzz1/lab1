package org.foo.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.foo.models.User;
import org.foo.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
public class AuthenticationController {

  private final AuthenticationService authenticationService;
  private final UserRepository userRepository;

  public AuthenticationController(AuthenticationService authenticationService, UserRepository userRepository) {
    this.authenticationService = authenticationService;
    this.userRepository = userRepository;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
    AuthenticationResponse response = authenticationService.login(request);
    return ResponseEntity.ok()
      .header(HttpHeaders.AUTHORIZATION, response.accesstoken())
      .body(response);


  }
  @PostMapping("/login222")
  public ResponseEntity<AuthenticationResponse> register(
    @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(authenticationService.login(request));
  }
  @PostMapping("/refresh-token")
  public void refreshToken(
    HttpServletRequest request,
    HttpServletResponse response
  ) throws IOException {
    authenticationService.refreshToken(request, response);
  }

  @GetMapping("/")
  public List<User> getAllUsers() {
    System.out.println("Dreni");
    return userRepository.findAll();

  }

}
