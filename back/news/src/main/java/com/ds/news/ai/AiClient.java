package com.ds.news.ai;

import com.ds.news.models.record.AiEnrichmentResult;
import reactor.core.publisher.Mono;

public interface AiClient {
    Mono<AiEnrichmentResult> enrich(String text);

}
