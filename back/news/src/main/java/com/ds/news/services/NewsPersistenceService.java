package com.ds.news.services;

import com.ds.news.models.payloads.RawNewsPayload;
import com.ds.news.repositories.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsPersistenceService {
    private final NewsRepository newsRepository;

    public Mono<RawNewsPayload> persistAndEnrich(RawNewsPayload payload) {
        payload.setLanguage(detectLanguage(payload.getTitle()));

        return newsRepository.save(payload)
                .doOnSuccess(saved -> log.info("News persisted successfully {}", saved))
                .doOnError(err -> log.error("News persisted failed ",err));
    }

    private String detectLanguage(String content) {
        if (content == null) return "unknown";
        return "en";
    }
}
