package com.ds.user_service.controller;

import com.ds.user_service.model.dto.InterestedNewsResponse;
import com.ds.user_service.model.dto.PreferenceRequest;
import com.ds.user_service.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class ProfileController {
    private final UserProfileService userProfileService;
    @PostMapping("/interested")
    Flux<ResponseEntity<InterestedNewsResponse>> getUserInterest(@RequestBody PreferenceRequest request){
        return userProfileService
                .getUserInterests(request.preferredCategories(),request.blockedSources())
                .map(res->ResponseEntity.ok(res));
    }

}
