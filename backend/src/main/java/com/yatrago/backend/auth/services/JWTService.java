package com.yatrago.backend.auth.services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.yatrago.backend.entity.UsersEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    @Value("${jwt.expirationRefresh}")
    Long expirationRefreshToken;

    @Value("${jwt.expirationAccess}")
    Long expirationAccessToken;

    @Value("${jwt.secret}")
    String jwtSecret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(UsersEntity user){
        return Jwts.builder().subject(user.getId().toString()).claim("email", user.getEmail()).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+expirationAccessToken)).signWith(getSigningKey()).compact();
    }

    public String generateRefreshToken(UsersEntity user){
        return Jwts.builder().subject(user.getId().toString()).claim("email", user.getEmail()).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+expirationRefreshToken)).signWith(getSigningKey()).compact();
    }

    public String validateJWTToken(String token){
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            Claims claims = Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
            String email = claims.get("email", String.class);
            return email;
        } catch (Exception e) {
            return null;
        }
    }
    
}
