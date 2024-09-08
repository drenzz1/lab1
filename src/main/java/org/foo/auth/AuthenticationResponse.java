package org.foo.auth;

import lombok.Builder;
import org.foo.dto.UserDto;

@Builder
public record AuthenticationResponse(String accessToken , String refreshToken) {
}
