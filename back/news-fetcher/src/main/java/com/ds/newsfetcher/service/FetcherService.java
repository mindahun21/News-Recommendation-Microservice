package com.ds.newsfetcher.service;

import com.ds.newsfetcher.config.NewsProviderProperties;
import com.ds.newsfetcher.models.payload.RawNewsPayload;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@RequiredArgsConstructor
public class FetcherService {
    private final NewsProviderProperties properties;
    private final ProviderService providerService;
    private final KafkaSender<String, RawNewsPayload> kafkaSender;
    private final DeduplicationService deduplicationService;

    @Value("raw-news")
    private String topic;

    public Mono<Void> fetchAll() {
        return Flux.fromIterable(properties.getProviders())
                .flatMap(providerService::fetchFrom)
                .flatMap(payload ->
                        deduplicationService.isNew(payload.getDedupKey())
                                .filter(Boolean::booleanValue)
                                .map(ok->payload)
                )
                .map(article -> SenderRecord.create(
                        new ProducerRecord<>(
                                topic,
                                article.getProvider() + article.getCategory(),
                                article
                        ),
                        article.getExternalId()
                ))
                .as(kafkaSender::send)
                .then();
    }

}
