package com.ds.newsfetcher.publisher;

import com.ds.newsfetcher.models.payload.RawNewsPayload;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaProducerConfig {
    @Bean
    public SenderOptions<String, RawNewsPayload> senderOptions(ProducerFactory<String, RawNewsPayload> producerFactory) {
        Map<String, Object> config = new HashMap<>(producerFactory.getConfigurationProperties());
        return SenderOptions.create(config);
    }

    @Bean
    public KafkaSender<String, RawNewsPayload> kafkaSender(SenderOptions<String, RawNewsPayload> senderOptions) {
        return KafkaSender.create(senderOptions);
    }

}
