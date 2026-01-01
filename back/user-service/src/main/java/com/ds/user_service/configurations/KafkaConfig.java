package com.ds.user_service.configurations;

import com.ds.user_service.model.dto.analytics.AnalyticsEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ProducerFactory;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;

    @Value("${analytics.category_topic}")
    private String categoryTopic;

    @Value("${analytics.source_topic}")
    private String sourceTopic;

    @Bean
    public SenderOptions<String, AnalyticsEvent> senderOptions(ProducerFactory<String, AnalyticsEvent> producerFactory) {
        Map<String, Object> props = new HashMap<>(producerFactory.getConfigurationProperties());
        return SenderOptions.create(props);
    }

    @Bean
    public KafkaSender<String, AnalyticsEvent> kafkaSender(SenderOptions<String, AnalyticsEvent> senderOptions) {
        return KafkaSender.create(senderOptions);
    }

    @Bean
    public ReceiverOptions<String, AnalyticsEvent> receiverOptions() {
        Map<String, Object> props = new HashMap<>(kafkaProperties.buildConsumerProperties());
        return ReceiverOptions.<String, AnalyticsEvent>create(props)
                .subscription(Arrays.asList(categoryTopic, sourceTopic));
    }

    @Bean
    public KafkaReceiver<String, AnalyticsEvent> kafkaReceiver(ReceiverOptions<String, AnalyticsEvent> receiverOptions) {
        return KafkaReceiver.create(receiverOptions);
    }
}
