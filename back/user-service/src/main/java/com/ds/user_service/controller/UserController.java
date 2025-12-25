package com.ds.user_service.controller;

import com.ds.user_service.model.dto.UserRequest;
import com.ds.user_service.model.dto.UserCreateResponse;
import com.ds.user_service.model.dto.UserRequest;
import com.ds.user_service.model.dto.UserResponse;
import com.ds.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final AuthService authService;
    @PostMapping
    Mono<ResponseEntity<UserCreateResponse>> createUser(
            @RequestBody UserRequest user
    ){
        return authService.createUser(user)
                .map(r->ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(r));
    }
    
    @GetMapping("/refresh")
    ResponseEntity<Map<String,String>> refresh(
            @CookieValue(value = "refresh-token") String refreshToken
    ){
        return ResponseEntity.ok(
                authService.refreshAccessToken(refreshToken));
    }
    @PostMapping("/login")
    ResponseEntity<Map<String,String>> authenticate(
            @RequestBody UserRequest u
    ){
        return ResponseEntity.ok(authService.authenticate(u));
    }
}
