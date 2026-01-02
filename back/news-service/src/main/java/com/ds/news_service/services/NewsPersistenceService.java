package com.ds.news_service.services;

import com.ds.news_service.ai.AiClient;
import com.ds.news_service.models.payloads.RawNewsPayload;
import com.ds.news_service.repositories.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsPersistenceService {
    private final NewsRepository newsRepository;
    private final AiClient aiClient;

    public Mono<RawNewsPayload> persistAndEnrich(RawNewsPayload payload) {
        String semanticText = buildNewsSemanticText(payload);

        return aiClient.enrich(semanticText)
                .map(result -> {
                    payload.setAiSummary(payload.getDescription());
                    payload.setEmbedding(result.embedding());
                    payload.setLanguage(result.language());
                    return payload;
                })
                .flatMap(newsRepository::save)
                .doOnSuccess(saved ->
                        log.info("News enriched and persisted {}", saved.getId())
                );
    }

    private String buildNewsSemanticText(RawNewsPayload news) {
        return """
        Title: %s
        Description: %s
        Category: %s
        Author: %s
        Content: %s
        """.formatted(
                    safe(news.getTitle()),
                    safe(news.getDescription()),
                    safe(news.getCategory()),
                    safe(news.getAuthor()),
                    safe(news.getContent())
            );
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
