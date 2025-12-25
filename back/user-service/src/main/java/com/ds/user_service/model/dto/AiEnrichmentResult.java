package com.ds.user_service.model.dto;

public record AiEnrichmentResult(
//        String summary,
        float[] embedding,
        String language
) {
}