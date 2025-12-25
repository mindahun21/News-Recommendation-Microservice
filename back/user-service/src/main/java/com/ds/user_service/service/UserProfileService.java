package com.ds.user_service.service;

import com.ds.user_service.configurations.ExternalServiceClient;
import com.ds.user_service.model.*;
import com.ds.user_service.model.dto.InterestedNewsResponse;
import com.ds.user_service.model.dto.UserCreateResponse;
import com.ds.user_service.model.dto.UserProfileUpdateRequest;
import com.ds.user_service.model.dto.UserResponse;
import com.ds.user_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserPreferenceRepository preferenceRepository;
    private final UserRepository userRepository;
    private final DemographicsRepository demographicsRepository;
    private final EngagementMetricsRepository engagementMetricsRepository;
    private final UserRecommendationProfileRepository userRecommendationProfileRepository;
    private final R2dbcEntityTemplate template;
    private final ExternalServiceClient externalServiceClient;

    public Flux<UserCreateResponse> getAllUsers(){
        return userRepository.findAll()
                .flatMap(user -> Mono.zip(
                        preferenceRepository.findById(user.getUsername()).defaultIfEmpty(new Preferences()),
                        userRecommendationProfileRepository.findById(user.getUsername()).defaultIfEmpty(new UserRecommendationProfile()),
                        demographicsRepository.findById(user.getUsername()).defaultIfEmpty(new Demographics()),
                        engagementMetricsRepository.findById(user.getUsername()).defaultIfEmpty(new EngagementMetrics())
                ).map(tuple -> UserMapper.toUserCreateResponse(
                        user,
                        tuple.getT1(),
                        tuple.getT2(),
                        tuple.getT3(),
                        tuple.getT4()
                )));
    }

    public Mono<UserCreateResponse> updateUserProfile(String userId, UserProfileUpdateRequest request) {
        return userRepository.findById(userId)
                .flatMap(user -> Mono.zip(
                        updateOrInsertPreferences(userId, request),
                        updateOrInsertDemographics(userId, request),
                        userRecommendationProfileRepository.findById(userId)
                                .defaultIfEmpty(new UserRecommendationProfile()),
                        engagementMetricsRepository.findById(userId)
                                .defaultIfEmpty(new EngagementMetrics())
                ).map(tuple -> UserMapper.toUserCreateResponse(
                        user,
                        tuple.getT1(), // Preferences
                        tuple.getT3(), // UserRecommendationProfile
                        tuple.getT2(), // Demographics
                        tuple.getT4()  // EngagementMetrics
                )))
                .doOnSuccess(result -> {
                    if (request.preferredCategories() != null && !request.preferredCategories().isEmpty()) {
                        updateEmbedding(userId, request.preferredCategories()).subscribe();
                    }
                });
    }

    private Mono<Void> updateEmbedding(String userId, List<String> preferredCategories) {
        String semanticText = String.join(" ", preferredCategories);
        return externalServiceClient.enrich(semanticText)
                .flatMap(result -> {
                    // Convert float[] to Float[] for database storage
                    Float[] embeddingBoxed = new Float[result.embedding().length];
                    for (int i = 0; i < result.embedding().length; i++) {
                        embeddingBoxed[i] = result.embedding()[i];
                    }
                    
                    return userRecommendationProfileRepository.findById(userId)
                            .switchIfEmpty(Mono.defer(() -> {
                                UserRecommendationProfile newProfile = new UserRecommendationProfile();
                                newProfile.setId(userId);
                                return Mono.just(newProfile);
                            }))
                            .flatMap(profile -> {
                                profile.setPreferenceEmbedding(embeddingBoxed);
                                profile.setEmbeddingUpdatedAt(java.time.Instant.now());
                                return userRecommendationProfileRepository.save(profile);
                            });
                })
                .then();
    }

    private Mono<Preferences> updateOrInsertPreferences(String userId, UserProfileUpdateRequest request) {
        return preferenceRepository.findById(userId)
                .map(pref -> {
                    if (request.preferredCategories() != null)
                        pref.setPreferredCategories(request.preferredCategories().stream().map(String::toLowerCase).toList());
                    if (request.blockedSources() != null)
                        pref.setBlockedSources(request.blockedSources().stream().map(String::toLowerCase).toList());
                    return pref;
                })
                .flatMap(preferenceRepository::save)
                .switchIfEmpty(Mono.defer(() -> {
                    Preferences newPref = new Preferences();
                    newPref.setUserId(userId);
                    if (request.preferredCategories() != null)
                        newPref.setPreferredCategories(request.preferredCategories().stream().map(String::toLowerCase).toList());
                    if (request.blockedSources() != null)
                        newPref.setBlockedSources(request.blockedSources().stream().map(String::toLowerCase).toList());
                    return template.insert(newPref);
                }));
    }

    private Mono<Demographics> updateOrInsertDemographics(String userId, UserProfileUpdateRequest request) {
        return demographicsRepository.findById(userId)
                .map(demo -> {
                    if (request.location() != null) demo.setLocation(request.location().toLowerCase());
                    if (request.ageRange() != null) demo.setAgeRange(request.ageRange().toLowerCase());
                    return demo;
                })
                .flatMap(demographicsRepository::save)
                .switchIfEmpty(Mono.defer(() -> {
                    Demographics newDemo = new Demographics();
                    newDemo.setUserId(userId);
                    if (request.location() != null) newDemo.setLocation(request.location().toLowerCase());
                    if (request.ageRange() != null) newDemo.setAgeRange(request.ageRange().toLowerCase());
                    return template.insert(newDemo);
                }));
    }


    public Flux<InterestedNewsResponse> getUserInterests(
            List<String> preferred,
            List<String> blockedSources
    ) {
        return preferenceRepository
                .findPreferencesByPreferredCategories(preferred.toArray(new String[0]))
                .filter(pref -> {
                    List<String> blocked = Optional
                            .ofNullable(pref.getBlockedSources())
                            .orElse(List.of())
                            .stream()
                            .map(String::toLowerCase)
                            .toList();

                    List<String> requestBlocked = blockedSources
                            .stream()
                            .map(String::toLowerCase)
                            .toList();

                    return blocked.stream().noneMatch(requestBlocked::contains);
                })
                .flatMap(pref -> {
                    String userId = pref.getUserId();
                    
                    return Mono.zip(
                            demographicsRepository.findById(userId).defaultIfEmpty(new Demographics()),
                            userRecommendationProfileRepository.findById(userId).defaultIfEmpty(new UserRecommendationProfile())
                    ).map(tuple -> {
                        Demographics demographics = tuple.getT1();
                        UserRecommendationProfile profile = tuple.getT2();
                        
                        return InterestedNewsResponse.builder()
                                .userId(userId)
                                .preferences(pref)
                                .demographics(demographics)
                                .preferenceEmbedding(profile.getPreferenceEmbedding() != null 
                                    ? toPrimitiveArray(profile.getPreferenceEmbedding()) 
                                    : null)
                                .embeddingUpdatedAt(profile.getEmbeddingUpdatedAt())
                                .build();
                    });
                });
    }

    public Mono<Void> updateAllUserEmbeddings() {
        return preferenceRepository.findAll()
                .filter(pref -> pref.getPreferredCategories() != null 
                        && !pref.getPreferredCategories().isEmpty())
                .flatMap(pref -> updateEmbedding(pref.getUserId(), pref.getPreferredCategories())
                        .onErrorResume(error -> {
                            // Log error but continue processing other users
                            System.err.println("Failed to update embedding for user: " 
                                    + pref.getUserId() + ", error: " + error.getMessage());
                            return Mono.empty();
                        }))
                .then();
    }
    
    private float[] toPrimitiveArray(Float[] array) {
        if (array == null) return null;
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] != null ? array[i] : 0f;
        }
        return result;
    }
}
