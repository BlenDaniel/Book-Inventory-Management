package com.book.inventory.management.bookim.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

        @Value("${app.jwt-secret}")
        private String jwtSecret;

        @Value("${app.jwt-expiration-milliseconds}")
        private long jwtExpirationDate;

        // Generate JWT token
        public String generateToken(Authentication authentication) {
                String username = authentication.getName();

                Date currentDate = new Date();
                Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

                return Jwts.builder()
                                .setSubject(username)
                                .setIssuedAt(currentDate)
                                .setExpiration(expireDate)
                                .signWith(key())
                                .compact();
        }

        private Key key() {
                return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        }

        // Get username from JWT token
        public String getUsername(String token) {
                Claims claims = Jwts.parserBuilder()
                                .setSigningKey(key())
                                .build()
                                .parseClaimsJws(token)
                                .getBody();

                return claims.getSubject();
        }

        // Validate JWT Token
        public boolean validateToken(String token) {
                try {
                        Jwts.parserBuilder()
                                        .setSigningKey(key())
                                        .build()
                                        .parseClaimsJws(token);
                        return true;
                } catch (Exception e) {
                        // Log the exception
                        return false;
                }
        }
}
