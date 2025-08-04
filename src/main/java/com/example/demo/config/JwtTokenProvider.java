package com.example.demo.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("gizliAnahtarGibiUzunBirMetin1234567890".getBytes());
    private final long EXPIRATION_TIME = 86400000; // 1 gün

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());

        return Jwts.builder()
                .setClaims(claims)        // claim’leri ayarla
                .setSubject(userDetails.getUsername()) // token sahibini ayarla
                .setIssuedAt(new Date())  // oluşturulma zamanı
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // son kullanma
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // imzala
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("JWT doğrulama hatası: " + e.getMessage());
            return false;
        }

    }
}
