package com.ds.user_service.service;

import com.ds.user_service.configurations.JWTAuthenticationManager;
import com.ds.user_service.exceptions.UserAlreadyExistsException;
import com.ds.user_service.model.User;
import com.ds.user_service.model.UserMapper;
import com.ds.user_service.model.dto.UserRequest;
import com.ds.user_service.model.dto.UserResponse;
import com.ds.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService  jwtService;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTAuthenticationManager jwtAuthenticationManager;
    private final R2dbcEntityTemplate template;
    public  Mono<UserResponse> createUser(UserRequest user) {
        repository.existsById(user.username())
                .map(
                        exists-> exists?
                                Mono.empty():
                                Mono.error(new UserAlreadyExistsException(String.format("A user with the username: %s already exists",user.username())))
                );
        return template.insert(UserMapper.toUser(user.username(),passwordEncoder.encode(user.password()))).
        map(UserMapper::toUserResponse);
    }
    public Map<String, String> refreshAccessToken(String refreshToken) {
        var username=jwtService.getUserName(refreshToken);
        return Map.of("token",jwtService.generateToken(username));
    }

    public  Map<String, String> authenticate(UserRequest u) {
        try{

                jwtAuthenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        u.username(),
                        u.password()
                )
        );
        }
        catch(Exception e){
            System.out.println(e);
        }
        return Map.of(
                        "token",jwtService.generateToken(u.username()),
                    "refresh",jwtService.generateRefreshToken(u.username())
                );
    }
}
