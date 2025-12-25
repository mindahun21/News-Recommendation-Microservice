package com.ds.user_service.service;

import com.ds.user_service.model.dto.InterestedNewsResponse;
import com.ds.user_service.repository.UserPreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserPreferenceRepository preferenceRepository;

    public Flux<InterestedNewsResponse> getUserInterests(List<String> preferred, List<String> blockedSources) {
    InterestedNewsResponse.InterestedNewsResponseBuilder builder=InterestedNewsResponse.builder();

        return preferenceRepository.findPreferencesByPreferredCategories(preferred)
                .filter(
                pref->!pref.getBlockedSources()
                        .stream()
                        .anyMatch(source->blockedSources.contains(source))
        )
                .map(preferences -> builder
                        .preferences(preferences)
                        .build()
                );
    };
}
