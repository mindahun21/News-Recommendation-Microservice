package com.ds.recommendationservice.config;

import com.ds.recommendationservice.models.AiEnrichmentResult;
import com.ds.recommendationservice.models.EnrichedNewsPayload;
import com.ds.recommendationservice.models.UserRecommendationProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ExternalServiceClient implements ServiceToServiceClient {
    private final WebClient webClient;

    @Value("${uri.embedding_uri}")
    private String embedding_uri;

    @Value("${uri.user_service}")
    private String user_service;

    @Override
    public Mono<AiEnrichmentResult> enrich(String text) {
        return webClient.post()
                .uri(embedding_uri+"/enrich")
                .bodyValue(Map.of(
                        "text", text
                ))
                .retrieve()
                .bodyToMono(AiEnrichmentResult.class);
    }

    @Override
    public Flux<UserRecommendationProfile> findInterestedUsers(EnrichedNewsPayload news) {
        List<String> preferredCategories = List.of(news.getCategory());
        List<String> blockedSources = List.of(news.getProvider());

        return webClient.post()
                .uri(user_service+"/users/interested")
                .bodyValue(Map.of(
                        "preferredCategories", preferredCategories,
                        "blockedSources", blockedSources
                ))
                .retrieve()
                .bodyToFlux(UserRecommendationProfile.class);
    }

}