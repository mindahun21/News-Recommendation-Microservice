package com.ds.news.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewsConsumerRunner {
    private final NewsConsumerService consumerService;

    @PostConstruct
    public void start(){
        log.info(">>>NewsConsumerRunner started<<<");
        consumerService.consume();
    }
}
