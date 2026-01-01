package com.ds.news_service.services;

import com.ds.news_service.models.payloads.RawNewsPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsConsumerService {
    private final KafkaReceiver<String, RawNewsPayload> kafkaReceiver;
    private final NewsPersistenceService newsPersistenceService;
    private final KafkaSender<String, RawNewsPayload> kafkaSender;

    @Value("${news.enriched_news_topic}")
    private String newsEnrichedNewsTopic;

    public void consume(){
        kafkaReceiver.receive()
                .map(ConsumerRecord::value)
                .flatMap(newsPersistenceService::persistAndEnrich)
                .map(enriched ->
                        SenderRecord.create(
                                new ProducerRecord<>(
                                        newsEnrichedNewsTopic,
                                        enriched.getProvider() + enriched.getCategory(),
                                        enriched
                                ),
                                enriched.getExternalId()
                        )
                )
                .as(kafkaSender::send)
                .doOnNext(result -> {
                    RecordMetadata meta = result.recordMetadata();
                    log.info(
                            "Kafka send success | topic={} partition={} offset={} key={}",
                            meta.topic(),
                            meta.partition(),
                            meta.offset(),
                            result.correlationMetadata()
                    );
                })
                .subscribe(
                        success -> log.info(">>>NewsConsumerService consume success<<<"),
                        error -> log.error("Error consuming news", error)
                );
    }
}
