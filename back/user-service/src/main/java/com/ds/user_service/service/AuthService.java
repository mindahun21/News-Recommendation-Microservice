package com.ds.user_service.service;

import com.ds.user_service.exceptions.UserAlreadyExistsException;
import com.ds.user_service.model.User;
import com.ds.user_service.model.UserMapper;
import com.ds.user_service.model.dto.UserRequest;
import com.ds.user_service.model.dto.UserResponse;
import com.ds.user_service.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService  jwtService;
    private final UserRepository repository;
    private final UserMapper mapper;
    private final AuthenticationManager authenticationManager;

    public  UserResponse createUser(UserRequest user) {
        if(repository.existsById(user.username()))
            throw new UserAlreadyExistsException(String.format("A user with the username: %s already exists",user.username()));
        return mapper
                .toUserResponse(
                        repository.save(mapper.toUser(user))
                );
    }

    public Map<String, String> refreshAccessToken(String refreshToken) {
        var username=jwtService.getUserName(refreshToken);
        return Map.of("token",jwtService.generateToken(username));
    }

    public  Map<String, String> authenticate(HttpServletResponse response, UserRequest u) {
                authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        u.username(),
                        u.password()
                )
        );
        Cookie cookie=new Cookie("refresh-token", jwtService.generateRefreshToken(u.username()));
        cookie.setHttpOnly(true);
        cookie.setPath("api/users/refresh");
        cookie.setMaxAge(jwtService.getExpiration());
        response.addCookie(cookie);
        return Map.of(
                        "token",jwtService.generateToken(u.username())
                );
    }
}
