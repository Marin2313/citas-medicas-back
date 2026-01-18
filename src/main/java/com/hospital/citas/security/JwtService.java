package com.hospital.citas.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expirationMinutes:240}")
  private long expirationMinutes;

  private Key key() {
    // HS256 requiere key >= 256 bits (32 bytes)
    return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(UserDetails user) {
    List<String> roles = user.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .toList();

    Instant now = Instant.now();
    Instant exp = now.plus(expirationMinutes, ChronoUnit.MINUTES);

    return Jwts.builder()
      .setSubject(user.getUsername())
      .setIssuedAt(Date.from(now))
      .setExpiration(Date.from(exp))
      .addClaims(Map.of("roles", roles))
      .signWith(key())
      .compact();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> resolver) {
    Claims claims = Jwts.parserBuilder()
      .setSigningKey(key())
      .build()
      .parseClaimsJws(token)
      .getBody();
    return resolver.apply(claims);
  }

  public boolean isTokenValid(String token, UserDetails user) {
    String username = extractUsername(token);
    return username.equals(user.getUsername()) && extractExpiration(token).after(new Date());
  }
}
