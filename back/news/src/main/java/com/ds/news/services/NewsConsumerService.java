package com.ds.news.services;

import com.ds.news.models.payloads.RawNewsPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsConsumerService {
    private final KafkaReceiver<String, RawNewsPayload> kafkaReceiver;
    private final NewsPersistenceService newsPersistenceService;

    public void consume(){
        kafkaReceiver.receive()
                .map(ConsumerRecord::value)
                .flatMap(newsPersistenceService::persistAndEnrich)
                .subscribe(
                        success -> log.info(">>>NewsConsumerService consume success<<<"),
                        error -> log.error("Error consuming news", error)
                );
    }
}
