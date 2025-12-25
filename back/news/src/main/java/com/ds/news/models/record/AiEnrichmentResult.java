package com.ds.news.models.record;

public record AiEnrichmentResult(
//        String summary,
        float[] embedding,
        String language
) {
}
