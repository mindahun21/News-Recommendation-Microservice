package com.ds.user_service.converter;

import com.ds.user_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {
    private static final int BEARER_PREFIX_LENGTH = 7;
    private final JwtService service;

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(header -> header.startsWith("Bearer "))
                .map(header -> header.substring(BEARER_PREFIX_LENGTH))
                .flatMap(this::createAuthenticationIfValid);
    }

    private Mono<Authentication> createAuthenticationIfValid(String token) {
        if (service.validateToken(token)) {
            return Mono.just(new UsernamePasswordAuthenticationToken(
                    service.getUserName(token), token, List.of()));
        }
        return Mono.empty();
    }
}