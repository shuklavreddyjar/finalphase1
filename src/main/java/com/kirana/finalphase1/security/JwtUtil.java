package com.kirana.finalphase1.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * The type Jwt util.
 */
@Component
public class JwtUtil {

    /**
     * NOTE:
     * In production, move this to environment variables.
     */
    private static final String SECRET_KEY =
            "shuklavreddy-shuklavreddy-shuklavreddy";

    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    private final SecretKey key =
            Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    /**
     * Generates JWT with MongoDB userId as subject.
     *
     * @param userId MongoDB ObjectId (hex string)
     * @param role   USER / ADMIN
     * @return the string
     */
    public String generateToken(String userId, String role) {

        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates JWT and returns claims.
     *
     * @param token the token
     * @return the claims
     */
    public Claims validateToken(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
