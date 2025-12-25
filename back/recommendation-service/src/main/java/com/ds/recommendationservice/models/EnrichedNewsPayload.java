package com.ds.recommendationservice.models;

import lombok.Data;

import java.time.Instant;

@Data
public class EnrichedNewsPayload {
    private String id;
    private String provider;
    private String externalId;     // optional
    private String title;
    private String description;
    private String content;
    private String author;
    private String category;
    private String sourceUrl;
    private Instant publishedAt;
    private Instant fetchedAt;
    private String dedupKey;
    private String language;
    private String aiSummary;          // short semantic summary
    private float[] embedding;         // vector representation

}
