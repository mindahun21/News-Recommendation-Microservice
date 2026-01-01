package com.ds.news_service.controllers;

import com.ds.news_service.models.payloads.RawNewsPayload;
import com.ds.news_service.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Component
@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/{id}")
    public Mono<RawNewsPayload>  getNews(@PathVariable String id) {
        return newsService.getNewsById(id);
    }

}
