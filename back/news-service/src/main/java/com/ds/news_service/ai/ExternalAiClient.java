package com.ds.news_service.ai;

import com.ds.news_service.models.record.AiEnrichmentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ExternalAiClient implements AiClient {
    private final WebClient webClient;

    @Value("${ai.embedding_uri}")
    private String embedding_uri;

    @Override
    public Mono<AiEnrichmentResult> enrich(String text) {
        return webClient.post()
                .uri(embedding_uri+"/enrich")
                .bodyValue(Map.of(
                        "text", text
                ))
                .retrieve()
                .bodyToMono(AiEnrichmentResult.class);
    }
}
