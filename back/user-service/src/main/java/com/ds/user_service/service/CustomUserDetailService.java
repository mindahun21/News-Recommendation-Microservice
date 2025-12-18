package com.ds.user_service.service;

import com.ds.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements ReactiveUserDetailsService {
    private  final UserRepository repository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return repository
                .findById(username)
                .map(user-> User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole().toArray(new String[0]))
                        .build());
    }
}
