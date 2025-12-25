package com.ds.user_service.controller;

import com.ds.user_service.model.dto.InterestedNewsResponse;
import com.ds.user_service.model.dto.PreferenceRequest;
import com.ds.user_service.model.dto.UserCreateResponse;
import com.ds.user_service.model.dto.UserProfileUpdateRequest;
import com.ds.user_service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ProfileController {
    private final UserProfileService userProfileService;

    @GetMapping
    Flux<UserCreateResponse> getAllUsers(){
        return userProfileService.getAllUsers();
    }

    @PatchMapping("/{userId}")
    Mono<ResponseEntity<UserCreateResponse>> updateUserProfile(
            @PathVariable String userId,
            @RequestBody UserProfileUpdateRequest request
    ){
        return userProfileService.updateUserProfile(userId, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/interested")
    Flux<InterestedNewsResponse> getUserInterest(@RequestBody PreferenceRequest request){
        return userProfileService
                .getUserInterests(request.preferredCategories(),request.blockedSources());
    }

    @PostMapping("/embeddings/refresh")
    Mono<ResponseEntity<String>> updateAllUserEmbeddings(){
        return userProfileService.updateAllUserEmbeddings()
                .then(Mono.just(ResponseEntity.ok("Embedding update initiated for all users")));
    }

}
