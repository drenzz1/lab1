package org.foo.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface AuthenticationService {
  AuthenticationResponse login(AuthenticationRequest authenticationRequest);
  void refreshToken(HttpServletRequest request,
                    HttpServletResponse response) throws IOException;
}
