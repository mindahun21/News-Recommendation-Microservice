package com.ds.recommendationservice.config;

import com.ds.recommendationservice.models.AiEnrichmentResult;
import com.ds.recommendationservice.models.EnrichedNewsPayload;
import com.ds.recommendationservice.models.UserRecommendationProfile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ServiceToServiceClient {
    Mono<AiEnrichmentResult> enrich(String text);

    default Mono<float[]> embed(String text) {
        return enrich(text)
                .map(AiEnrichmentResult::embedding);
    }

    Flux<UserRecommendationProfile> findInterestedUsers(EnrichedNewsPayload news);
}
