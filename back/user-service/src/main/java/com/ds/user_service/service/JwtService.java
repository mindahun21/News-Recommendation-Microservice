package com.ds.user_service.service;

import com.ds.user_service.configurations.JwtConfig;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
@Service
public class JwtService {
    private final JwtConfig jwTconfig;
    private final SecretKey jwtSecret;
    public JwtService(JwtConfig jwtConfig) {
        this.jwTconfig = jwtConfig;
        this.jwtSecret=Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }
    public String generateToken(String username){
        return getToken(username, jwTconfig.getJwtAccessExpiration());
    }
    public String generateRefreshToken(String username){
        return getToken(username, jwTconfig.getExpiration());
    }
    private String getToken(String username,long expiration) {
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(jwtSecret)
                .compact();

    }

    public boolean validateToken(String token){
        try {
            var claim = Jwts
                    .parser()
                    .verifyWith(jwtSecret)
                    .build()
                    .parseClaimsJws(token);
            return claim.getBody().getExpiration().after(new Date());
        } catch (JwtException exception){
            return false;
        }
    }
    public String getUserName(String token){
        return Jwts
                .parser()
                .verifyWith(jwtSecret)
                .build()
                .parseSignedClaims(token)
                .getBody().getSubject();

    }
    public Integer getExpiration(){
        return (int)jwTconfig.getExpiration();
    }
}
