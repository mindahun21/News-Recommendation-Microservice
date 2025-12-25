package com.ds.recommendationservice.services;

import com.ds.recommendationservice.config.ServiceToServiceClient;
import com.ds.recommendationservice.models.EnrichedNewsPayload;
import com.ds.recommendationservice.models.UserRecommendationProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ServiceToServiceClient serviceToServiceClient;

    public Flux<UserRecommendationProfile> findInterestedUsers(EnrichedNewsPayload news) {

        return serviceToServiceClient.findInterestedUsers(news);
    }
}