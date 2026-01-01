package com.ds.news_service.services;

import com.ds.news_service.models.payloads.RawNewsPayload;
import com.ds.news_service.repositories.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;

    public Mono<RawNewsPayload> getNewsById(String id) {
        return newsRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "No such news with id " + id
                        )
                ));
    }
}
