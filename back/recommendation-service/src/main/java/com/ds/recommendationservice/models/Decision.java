package com.ds.recommendationservice.models;

public record Decision(
        EnrichedNewsPayload news,
        UserRecommendationProfile user,
        boolean relevant,
        String reason,
        Double score
) {

    public static Decision relevant(
            EnrichedNewsPayload news,
            UserRecommendationProfile user,
            double score,
            String reason
    ) {
        return new Decision(news, user, true, reason, score);
    }

    public static Decision notRelevant(
            EnrichedNewsPayload news,
            UserRecommendationProfile user,
            String reason
    ) {
        return new Decision(news, user, false, reason, null);
    }

    public boolean isRelevant() {
        return relevant;
    }
}

