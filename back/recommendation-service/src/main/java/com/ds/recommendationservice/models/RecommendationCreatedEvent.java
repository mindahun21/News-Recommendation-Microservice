package com.ds.recommendationservice.models;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class RecommendationCreatedEvent {
    private String userId;
    private String newsId;
    private Double score;
    private String reason;
    private Instant timestamp;

    public static RecommendationCreatedEvent from(Decision decision){
        return RecommendationCreatedEvent.builder()
                .userId(decision.user().getUserId())
                .newsId(decision.news().getId())
                .score(decision.score())
                .reason(decision.reason())
                .timestamp(Instant.now())
                .build();
    }

}
