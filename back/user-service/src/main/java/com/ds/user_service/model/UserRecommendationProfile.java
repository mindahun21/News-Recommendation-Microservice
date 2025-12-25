package com.ds.user_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table
@Data
public class UserRecommendationProfile {
    @Id
    private String id;
    private Float[]  preferenceEmbedding;
    private Instant embeddingUpdatedAt;
}
