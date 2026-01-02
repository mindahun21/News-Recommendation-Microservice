package com.ds.user_service.service;

import com.ds.user_service.model.dto.analytics.AnalyticsEvent;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalyticsConsumerService {

    private final UserProfileService userProfileService;
    private final KafkaReceiver<String, AnalyticsEvent> kafkaReceiver;

    @Value("${analytics.category_topic}")
    private String categoryTopic;

    @Value("${analytics.source_topic}")
    private String sourceTopic;

    @PostConstruct
    public void consume() {
        kafkaReceiver.receive()
                .flatMap(record -> {
                    AnalyticsEvent event = record.value();
                    String topic = record.topic();
                    log.info("Consumed event from topic {}: {}", topic, event);
                    
                    if (categoryTopic.equals(topic)) {
                        return userProfileService.handleCategoryEvent(event)
                                .doOnSuccess(v -> record.receiverOffset().acknowledge());
                    } else if (sourceTopic.equals(topic)) {
                        return userProfileService.handleSourceEvent(event)
                                .doOnSuccess(v -> record.receiverOffset().acknowledge());
                    }
                    
                    record.receiverOffset().acknowledge();
                    return reactor.core.publisher.Mono.empty();
                })
                .subscribe(
                        null,
                        error -> log.error("Error consuming analytics events", error)
                );
    }
}
