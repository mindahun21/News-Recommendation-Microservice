package com.ds.news.controllers;

import com.ds.news.models.payloads.RawNewsPayload;
import com.ds.news.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
