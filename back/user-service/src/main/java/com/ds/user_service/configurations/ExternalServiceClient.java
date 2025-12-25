package com.ds.user_service.configurations;

import com.ds.user_service.model.dto.AiEnrichmentResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ExternalServiceClient {

    private final WebClient webClient;

    @Value("${uri.embedding_uri}")
    private String embedding_uri;

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
