package com.ds.user_service.service;

import com.ds.user_service.configurations.JWTAuthenticationManager;
import com.ds.user_service.exceptions.UserAlreadyExistsException;
import com.ds.user_service.model.User;
import com.ds.user_service.model.UserMapper;
import com.ds.user_service.model.dto.UserRequest;
import com.ds.user_service.model.dto.UserCreateResponse;
import com.ds.user_service.model.dto.UserRequest;
import com.ds.user_service.model.dto.UserResponse;
import com.ds.user_service.model.Demographics;
import com.ds.user_service.model.EngagementMetrics;
import com.ds.user_service.model.Preferences;
import com.ds.user_service.model.UserRecommendationProfile;
import com.ds.user_service.repository.*;
import lombok.RequiredArgsConstructor;
import java.time.Instant;
import java.util.Collections;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService  jwtService;
    private final UserRepository repository;
    private final UserPreferenceRepository userPreferenceRepository;
    private final UserRecommendationProfileRepository userRecommendationProfileRepository;
    private final DemographicsRepository demographicsRepository;
    private final EngagementMetricsRepository engagementMetricsRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTAuthenticationManager jwtAuthenticationManager;
    private final R2dbcEntityTemplate template;

    @Transactional
    public  Mono<UserCreateResponse> createUser(UserRequest user) {
        return repository.existsById(user.username())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new UserAlreadyExistsException(String.format("A user with the username: %s already exists", user.username())));
                    }

                    User newUser = UserMapper.toUser(user.username(), passwordEncoder.encode(user.password()));

                    Preferences preferences = new Preferences();
                    preferences.setUserId(user.username());
                    preferences.setPreferredCategories(Collections.emptyList());
                    preferences.setBlockedSources(Collections.emptyList());

                    UserRecommendationProfile profile = new UserRecommendationProfile();
                    profile.setId(user.username());
                    profile.setPreferenceEmbedding(null);
                    profile.setEmbeddingUpdatedAt(Instant.now());

                    Demographics demographics = new Demographics();
                    demographics.setUserId(user.username());
                    demographics.setLocation("Unknown");
                    demographics.setAgeRange("Unknown");

                    EngagementMetrics metrics = new EngagementMetrics();
                    metrics.setUserId(user.username());
                    metrics.setLast_active(Instant.now());
                    metrics.setAverageReadTimeSeconds(0.0);
                    metrics.setTotalArticlesRead(0);

                    return template.insert(newUser)
                            .flatMap(savedUser -> Mono.zip(
                                    template.insert(preferences),
                                    template.insert(profile),
                                    template.insert(demographics),
                                    template.insert(metrics)
                            ).map(tuple -> UserMapper.toUserCreateResponse(
                                    savedUser,
                                    tuple.getT1(),
                                    tuple.getT2(),
                                    tuple.getT3(),
                                    tuple.getT4()
                            )));
                });
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
