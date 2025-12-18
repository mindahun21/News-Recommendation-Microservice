package com.ds.newsfetcher.models.payload;

import lombok.Data;

import java.time.Instant;

@Data
public class RawNewsPayload {
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
}
