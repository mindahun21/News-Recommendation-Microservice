package com.ds.news_service.models.record;

public record AiEnrichmentResult(
//        String summary,
        float[] embedding,
        String language
) {
}
