package com.ds.user_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("recommendationprofile")
@Data
public class UserRecommendationProfile {
    @Id
    @Column("user_id")
    private String id;
    @Column("preference_embedding")
    private Float[]  preferenceEmbedding;
    @Column("embedding_updated_at")
    private Instant embeddingUpdatedAt;
}
