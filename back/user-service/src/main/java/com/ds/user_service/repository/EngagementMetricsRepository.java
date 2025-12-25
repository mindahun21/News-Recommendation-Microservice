package com.ds.user_service.repository;

import com.ds.user_service.model.EngagementMetrics;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EngagementMetricsRepository extends ReactiveCrudRepository<EngagementMetrics, String> {
}
