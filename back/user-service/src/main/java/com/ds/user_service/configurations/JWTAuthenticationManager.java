package com.ds.user_service.configurations;

import com.ds.user_service.repository.UserRepository;
import com.ds.user_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtService jwtService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        String username = jwtService.getUserName(token);
        if (username != null && jwtService.validateToken(token)) {
            var auth = new PreAuthenticatedAuthenticationToken(
                    username,
                    null,
                    List.of()
            );
            return Mono.just(auth);
        }else  {
            return Mono.empty();
        }
    }

}
