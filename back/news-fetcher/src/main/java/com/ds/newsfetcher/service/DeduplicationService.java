package com.ds.newsfetcher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class DeduplicationService {
    private static final Duration TTL = Duration.ofHours(24);
    private final ReactiveRedisTemplate<String,String> reactiveRedisTemplate;

    public Mono<Boolean> isNew(String dedupKey){
        return reactiveRedisTemplate
                .opsForValue()
                .setIfAbsent(dedupKey, "1",TTL)
                .defaultIfEmpty(false);
    }
}
