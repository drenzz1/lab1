package org.foo.auth;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonPOJOBuilder
public record AuthenticationRequest(String username, String password) {
}
