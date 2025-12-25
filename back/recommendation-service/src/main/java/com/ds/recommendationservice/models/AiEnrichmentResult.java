package com.ds.recommendationservice.models;

public record AiEnrichmentResult(
//        String summary,
        float[] embedding,
        String language
) {
}