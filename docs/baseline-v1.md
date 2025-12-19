# Baseline v1.0

**Date:** 2025-12-19
**Status:** APPROVED

This document establishes Baseline v1.0 for the News Recommendation Microservice. It freezes the current configuration state of all critical integration points and data structures.

> [!IMPORTANT]
> Any changes to the items listed below require formal change control and version increment.

## 1. Kafka Integration

### Topic: `raw-news`
- **Purpose:** Transmits raw news articles fetched from external providers to the internal persistence service.
- **Producer:** `news-fetcher`
- **Consumer:** `news`
- **Payload Schema:** [news-raw-event.json](./schemas/news-raw-event.json)
    - **Key Fields:** `provider`, `title`, `sourceUrl`, `fetchedAt`

## 2. Database Schemas

### User Database (PostgreSQL)
- **Service:** `user-service`
- **Schema Version:** `V1` (Flyway)
- **Schema Definition:** [user-schema.json](./schemas/user-schema.json)
    - **Table:** `users`
    - **Columns:** `username` (PK), `password`, `role`

### News Database (MongoDB)
- **Service:** `news`
- **Collection:** `news`
- **Document Structure:** [news-event.json](./schemas/news-event.json)
    - **Key Fields:** `id`, `provider`, `title`, `language`

## 3. Critical Configuration Values

The following configuration values are frozen in this baseline. Changing them will break integration.

| Service | Property | Value (Baseline) |
| :--- | :--- | :--- |
| **news-fetcher** | `fetcher.topic` | `raw-news` |
| **news-fetcher** | `spring.kafka.producer.value-serializer` | `org.springframework.kafka.support.serializer.JsonSerializer` |
| **news** | `news.topic` | `raw-news` |
| **news** | `spring.kafka.consumer.value-deserializer` | `org.springframework.kafka.support.serializer.JsonDeserializer` |
| **news** | `spring.data.mongodb.uri` | `mongodb://.../news` |
| **user-service** | `spring.datasource.url` | `jdbc:postgresql://.../auth` |

## 4. Change Control Policy

1.  **No Ad-hoc Changes:** Direct modification of the schemas or critical configurations listed above is prohibited without a corresponding update to this baseline document.
2.  **Versioning:** Future changes must be captured in `baseline-v1.1.md` or `baseline-v2.0.md`.
3.  **Validation:** All code changes must be validated against these schemas to ensure backward compatibility.
