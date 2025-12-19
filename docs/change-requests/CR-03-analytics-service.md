# Change Request: Analytics Service

| Field | Value |
| :--- | :--- |
| **CR ID** | CR-03 |
| **Title** | Create Analytics Service |
| **Requested By** | mindahun21 |
| **Assigned To** | mindahun21 |
| **Date** | 2025-12-19 |
| **Status** | DRAFT |

## Affected Configuration Items
- **Service:** `analytics-service` (New)
- **Interface:** Kafka Topic (New: `analytics-events`)
- **Interface:** Kafka Topic (Existing: `user-events`, `raw-news`, `recommendation-events`)

## Current Baseline
- No centralized analytics service exists.

## Proposed Change Summary
Create a new microservice `analytics-service` to aggregate and analyze system events, user behavior, and content trends.

## Motivation
To gain insights into system usage, popular news categories, and user engagement metrics.

## Scope
1.  **Service Creation:** Initialize new Spring Boot service `analytics-service`.
2.  **Event Consumption:**
    - Consume `user-events` (registrations, updates).
    - Consume `raw-news` (content ingestion rates).
    - Consume `recommendation-events` (clicks, impressions).
3.  **Processing:** Implement stream processing (e.g., Kafka Streams) to calculate metrics like "Top 10 Categories", "Daily Active Users", "Click-Through Rate".
4.  **Publishing:** Publish aggregated insights to `analytics-events`.

## Impact Analysis
- **System:** Increases system complexity.
- **Resources:** High storage requirements for historical data.
- **Business:** Provides critical data for decision making.

## Proposed Interfaces

### Kafka Interfaces

#### 1. Consuming (Consumer)
- **Topic:** `user-events`
    - **Schema:** `user-recommendation-profile.json`
- **Topic:** `raw-news`
    - **Schema:** `news-raw-event.json`
- **Topic:** `recommendation-events`
    - **Schema:** `recommendation-event.json`

#### 2. Publishing (Producer)
- **Topic:** `analytics-events`
- **Schema:** `analytics-event.json` (See `docs/schemas/`)
- **Events:**
    - `USER_ENGAGEMENT`: Detailed engagement metrics (time spent, scroll depth).
    - `SYSTEM_METRIC`: Aggregated system stats (e.g., "articles_processed_per_minute").

## Concrete Tasks
1.  **Initialize Service:** Create Spring Boot project with Kafka Streams and Time-Series DB dependencies.
2.  **Implement Topology:** Define Kafka Streams topology to join and aggregate streams.
3.  **Implement Storage:** Configure sink to database (e.g., TimescaleDB).
4.  **Publish Insights:** Produce derived events to `analytics-events`.

## Configuration Management Considerations
- New service entry in `docker-compose.yml`.
- New database for analytics.
- New Kafka topic `analytics-events`.

## Approval Requirements
- Approval from (mindahun21).
