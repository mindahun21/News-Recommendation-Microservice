package com.ds.news.models.payloads;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "news")
public class RawNewsPayload {
    @Id
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
}
