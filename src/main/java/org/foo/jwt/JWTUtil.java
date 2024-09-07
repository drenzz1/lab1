package org.foo.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class JWTUtil {

  @Value("${application.security.jwt.secret-key}")
  private String secretKey;
  @Value("${application.security.jwt.expiration}")
  private long jwtExpiration;
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long refreshExpiration;

  public String issueToken(String subject) {
    return issueToken(subject, Map.of());
  }

  public String issueToken(String subject, String ...scopes) {
    return issueToken(subject, Map.of("scopes", scopes));
  }

  public String issueToken(String subject, List<String> scopes) {
    return issueToken(subject, Map.of("scopes", scopes));
  }


  public String issueToken(
    String subject,
    Map<String, Object> claims) {
    return buildToken(subject,claims,jwtExpiration);
  }
  public String generateRefreshToken(
    String subject
  ) {
    return buildToken(subject,new HashMap<>(),refreshExpiration);
  }
  private String buildToken(String subject,
                            Map<String, Object> claims,
                            Long expiration){
    return  Jwts.builder()
      .setClaims(claims)
      .setSubject(subject)
      .setIssuer("dreni")
      .setIssuedAt(Date.from(Instant.now()))
      .setExpiration(
        new Date(System.currentTimeMillis()+expiration)
      )
      .signWith(getSigningKey(), SignatureAlgorithm.HS256)
      .compact();

  }

  public String getSubject(String token) {
    return getClaims(token).getSubject();
  }

  private Claims getClaims(String token) {
    Claims claims = Jwts
      .parserBuilder()
      .setSigningKey(getSigningKey())
      .build()
      .parseClaimsJws(token)
      .getBody();
    return claims;
  }

  private Key getSigningKey() {

    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }

  public boolean isTokenValid(String jwt, String username) {
    String subject = getSubject(jwt);
    return subject.equals(username) && !isTokenExpired(jwt);
  }

  private boolean isTokenExpired(String jwt) {
    Date today = Date.from(Instant.now());
    return getClaims(jwt).getExpiration().before(today);
  }
  public boolean validateToken(String token) {
    try {
      Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token);
      return true;
    } catch (Exception ex) {
      throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
    }
  }

}
