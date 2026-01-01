package com.ds.news_service.ai;

import com.ds.news_service.models.record.AiEnrichmentResult;
import reactor.core.publisher.Mono;

public interface AiClient {
    Mono<AiEnrichmentResult> enrich(String text);

}
