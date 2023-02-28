package com.daria.realestate.configuration.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JWTProvider {
    private String secret;

    Logger logger = LoggerFactory.getLogger(JWTProvider.class);

    public JWTProvider() {
        secret = Base64.getEncoder().encodeToString("4zlUU5TzfLIyhHBqOgEd+Q==4zlUU5TzfLIyhHBqOgEd+Q==".getBytes());
    }

    public boolean validateJwtToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody();

            return !claims.getExpiration().before(new Date());
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String createToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 6000 * 1000))
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token);

        Collection<? extends GrantedAuthority> authorities = claims.getBody().get("roles", ArrayList.class);
        return new UsernamePasswordAuthenticationToken(
                claims.getSignature(),
                null,
                authorities);
    }
}
