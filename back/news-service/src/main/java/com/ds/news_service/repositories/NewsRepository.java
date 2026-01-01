package com.ds.news_service.repositories;

import com.ds.news_service.models.payloads.RawNewsPayload;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface NewsRepository extends ReactiveMongoRepository<RawNewsPayload, String> {
    Mono<RawNewsPayload> findByExternalId(String externalId);
}
