package com.ds.user_service.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.jwt")
public class JwtConfig {
    private String secret;
    private long expiration;
    private long jwtAccessExpiration;
    public long getJwtAccessExpiration() {
        return jwtAccessExpiration;
    }

    public void setJwtAccessExpiration(long jwtAccessExpiration) {
        this.jwtAccessExpiration = jwtAccessExpiration;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
