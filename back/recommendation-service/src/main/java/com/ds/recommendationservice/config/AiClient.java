package com.ds.recommendationservice.config;

import com.ds.recommendationservice.models.AiEnrichmentResult;
import reactor.core.publisher.Mono;

public interface AiClient {
    Mono<AiEnrichmentResult> enrich(String text);

    default Mono<float[]> embed(String text) {
        return enrich(text)
                .map(AiEnrichmentResult::embedding);
    }
}
