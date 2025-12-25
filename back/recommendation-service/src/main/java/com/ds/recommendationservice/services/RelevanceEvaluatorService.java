package com.ds.recommendationservice.services;

import com.ds.recommendationservice.models.Decision;
import com.ds.recommendationservice.models.EnrichedNewsPayload;
import com.ds.recommendationservice.models.UserRecommendationProfile;
import com.ds.recommendationservice.utils.VectorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RelevanceEvaluatorService {

    private static final double SIMILARITY_THRESHOLD = 0.35;

    public Mono<Decision> evaluate(
            EnrichedNewsPayload news,
            UserRecommendationProfile user
    ) {

        // Blocked sources
        if (user.getPreferences()
                .getBlockedSources()
                .contains(news.getProvider())) {

            return Mono.just(
                    Decision.notRelevant(news, user, "blocked source")
            );
        }

        //Embedding safety
        if (news.getEmbedding() == null || user.getPreferenceEmbedding() == null) {
            log.warn("Missing embeddings for user={} news={}",
                    user.getUserId(), news.getId());
            return Mono.just(
                    Decision.notRelevant(news, user, "missing embedding")
            );
        }

        //Semantic similarity
        double similarity = VectorUtils.cosineSimilarity(
                news.getEmbedding(),
                user.getPreferenceEmbedding()
        );

        if (similarity < SIMILARITY_THRESHOLD) {
            return Mono.just(
                    Decision.notRelevant(
                            news,
                            user,
                            "low similarity=" + similarity
                    )
            );
        }

        //Engagement check
//        Instant lastActive = user.getEngagementMetrics().getLastActive();
//        boolean activeRecently =
//                lastActive != null &&
//                        lastActive.isAfter(Instant.now().minus(30, ChronoUnit.DAYS));
//
//        if (!activeRecently) {
//            return Mono.just(
//                    Decision.notRelevant(news, user, "inactive user")
//            );
//        }

        // Relevant
        return Mono.just(
                Decision.relevant(
                        news,
                        user,
                        similarity,
                        "semantic similarity=" + similarity
                )
        );
    }
}

