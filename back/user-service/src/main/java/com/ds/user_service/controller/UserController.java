package com.ds.user_service.controller;

import com.ds.user_service.model.dto.UserRequest;
import com.ds.user_service.model.dto.UserResponse;
import com.ds.user_service.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final AuthService authService;
    @PostMapping
    ResponseEntity<UserResponse> createUser(
            @RequestBody UserRequest user
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.createUser(user));
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
            @RequestBody UserRequest u,
            HttpServletResponse response
    ){
        return ResponseEntity.ok(authService.authenticate(response,u));
    }
    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<?> handleUnauthenticated(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
