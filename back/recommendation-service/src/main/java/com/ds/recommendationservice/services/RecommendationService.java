package com.ds.recommendationservice.services;

import com.ds.recommendationservice.config.ExternalServiceClient;
import com.ds.recommendationservice.config.ServiceToServiceClient;
import com.ds.recommendationservice.models.Decision;
import com.ds.recommendationservice.models.EnrichedNewsPayload;
import com.ds.recommendationservice.models.RecommendationCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {

    private final UserService userService;
    private final RelevanceEvaluatorService relevanceEvaluatorService;
    private final RecommendationStore recommendationStore;
    private final KafkaSender<String, RecommendationCreatedEvent> kafkaSender;
    private final ServiceToServiceClient  serviceToServiceClient;
    private final EmailService emailService;

    @Value("${news.recommendation_created_topic}")
    private String newsRecommendationCreatedTopic;

    public Mono<Void> process(EnrichedNewsPayload news) {

        return userService.findInterestedUsers(news)
                .flatMap(user ->
                        relevanceEvaluatorService.evaluate(news, user)
                                .filter(Decision::isRelevant)
                                .flatMap(decision ->
                                        recommendationStore.saveToRedis(decision)
                                                .then(emailService.sendNewsRecommendation(user,news,user.getUserId()+"@gmail.com"))
                                )
                )
                .then();
    }

    private Mono<Void> publish(Decision decision) {

        RecommendationCreatedEvent event =
                RecommendationCreatedEvent.from(decision);

        return kafkaSender.send(
                        Mono.just(
                                SenderRecord.create(
                                        new ProducerRecord<>(
                                                newsRecommendationCreatedTopic,
                                                decision.user().getUserId(),
                                                event
                                        ),
                                        null
                                )
                        )
                )
                .doOnTerminate(() -> log.info(
                                "Published recommendation user={} news={} score={}",
                                decision.user().getUserId(),
                                decision.news().getId(),
                                decision.score()
                        )
                )
                .doOnError(err ->
                        log.error(
                                "Failed to publish recommendation user={} news={}",
                                decision.user().getUserId(),
                                decision.news().getId(),
                                err
                        )
                )
                .then();
    }

    public Flux<EnrichedRecommendation> getRecommendationForUser(String userId) {
        return recommendationStore.getRecommendations(userId)
                .flatMap(recommendation ->
                        serviceToServiceClient.getNewsById(recommendation.getNewsId())
                                .map(news -> {
                                    news.setEmbedding(null);
                                    return new EnrichedRecommendation(recommendation, news);
                                })
                );
    }

    @Data
    @AllArgsConstructor
    public static class EnrichedRecommendation {
        private RecommendationCreatedEvent recommendation;
        private EnrichedNewsPayload news;
    }

}
