package com.ds.news.config;

import com.ds.news.models.payloads.RawNewsPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class NewsKafkaConfig {
    private final KafkaProperties kafkaProperties;

    @Value("${news.topic}")
    private String topic;

    @Bean
    public SenderOptions<String, RawNewsPayload> senderOptions(ProducerFactory<String, RawNewsPayload> producerFactory) {
        Map<String, Object> config = new HashMap<>(producerFactory.getConfigurationProperties());
        return SenderOptions.create(config);
    }

    @Bean
    public KafkaSender<String, RawNewsPayload> kafkaSender(SenderOptions<String, RawNewsPayload> senderOptions) {
        return KafkaSender.create(senderOptions);
    }

    @Bean
    public ReceiverOptions<String, RawNewsPayload> receiverOptions() {
        Map<String, Object> config = new HashMap<>(kafkaProperties.buildConsumerProperties());
        return ReceiverOptions.<String, RawNewsPayload>create(config)
                .subscription(Collections.singleton(topic));
    }

    @Bean
    public KafkaReceiver<String, RawNewsPayload> kafkaReceiver(ReceiverOptions<String, RawNewsPayload> receiverOptions) {
        return KafkaReceiver.create(receiverOptions);
    }

}
