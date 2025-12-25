package com.ds.recommendationservice.models;

import lombok.*;
import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Document(collection = "users")
public class UserRecommendationProfile {

//    @Id
    private String userId;          // Unique internal identifier

    private Preferences preferences;
    private float[] preferenceEmbedding;
    private Instant embeddingUpdatedAt;
    private Demographics demographics;

//    private EngagementMetrics engagementMetrics;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Preferences {
        private List<String> preferredCategories;
        private List<String> blockedSources;
        private String userId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Demographics {
        private String ageRange;      // "18-24", "25-34", etc.
        private String location;      // ISO country code
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class EngagementMetrics {
        private Instant lastActive;
        private Double averageReadTimeSeconds;
        private Integer totalArticlesRead;
    }
}