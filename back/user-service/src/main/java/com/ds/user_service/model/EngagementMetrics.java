package com.ds.user_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table
@Data
public class EngagementMetrics {
    @Id
    private String userId;
    private Instant last_active;
    private Double averageReadTimeSeconds;
    private Integer totalArticlesRead;
}
