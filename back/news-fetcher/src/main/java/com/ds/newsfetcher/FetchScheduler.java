package com.ds.newsfetcher;

import com.ds.newsfetcher.service.FetcherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
@Slf4j
public class FetchScheduler {
    private final FetcherService fetcherService;
    private final AtomicBoolean running = new AtomicBoolean(false);

    @Scheduled(fixedDelayString = "PT1H")
    public void run() {
        if (!running.compareAndSet(false, true)) {
            log.warn("Previous fetch still running. Skipping this cycle.");
            return;
        }
        fetcherService.fetchAll()
                .doOnError(e -> log.error("Error while fetching", e))
                .doFinally(signal -> running.set(false))
                .subscribe();
    }
}
