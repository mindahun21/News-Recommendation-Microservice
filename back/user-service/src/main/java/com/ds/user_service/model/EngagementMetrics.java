package com.ds.user_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("engagement_metrics")
@Data
public class EngagementMetrics {
    @Id
    @Column("user_id")
    private String userId;
    @Column("last_active")
    private Instant last_active;
    @Column("average_read_time_seconds")
    private Double averageReadTimeSeconds;
    @Column("total_articles_read")
    private Integer totalArticlesRead;
}
