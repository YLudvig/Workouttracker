package com.workouttracker.workouttracker.Util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// Nyttjade denna guiden för att få in JWT-token för att förbättra säkerhetshanteringen : https://dev.to/rock_win_c053fa5fb2399067/step-by-step-guide-to-implementing-jwt-authentication-in-a-spring-boot-application-in-a-clean-ho1
// Fick sedan uppdatera till nyare JWT version och kombinerade den guiden med hur vi gjort i tidigare arbeten (bl.a. PlanningPoker och även personliga projekt där jag nyttjat JWT)
@Component
public class JwtTokenProvider {

    private final Key signingKey;
    // Sätter hur länge som en token anses giltig 
    private final long JWT_EXPIRATION = 604800000L;

    // Man nyttjar en secret key för att få ett unikt jwt system 
    public JwtTokenProvider(@Value("${jwt.secret}") String JWT_SECRET){
        this.signingKey = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // Genererar token 
    public String generateToken(String username){
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
            .signWith(signingKey, SignatureAlgorithm.HS512)
            .compact();
    }

    // Metod för att hämta userName baserat på token 
    public String getUserNameFromToken(String token){
        return Jwts.parserBuilder()    
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    // Metod för att validera/kontrollera token 
    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().
                setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException ex){
            return false;
        }
    }
    
}
