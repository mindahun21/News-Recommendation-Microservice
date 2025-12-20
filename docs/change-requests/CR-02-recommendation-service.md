# Change Request: Recommendation Service

| Field | Value                         |
| :--- |:------------------------------|
| **CR ID** | CR-02                         |
| **Title** | Create Recommendation Service |
| **Requested By** | mindahun21                    |
| **Assigned To** | mindahun21                    |
| **Date** | 2025-12-19                    |
| **Status** | COMPLETED                     |

## Affected Configuration Items
- **Service:** `recommendation-service` (New)
- **Interface:** Kafka Topic (New: `recommendation-events`)
- **Interface:** Kafka Topic (Existing: `user-events`, `raw-news`)

## Current Baseline
- No recommendation service exists. News is served without personalization.

## Proposed Change Summary
Create a new microservice `recommendation-service` responsible for generating personalized news recommendations for users by analyzing user profiles and news content.

## Motivation
To improve user engagement and retention by showing relevant news content based on user preferences and history.

## Scope
1.  **Service Creation:** Initialize new Spring Boot service `recommendation-service`.
2.  **Event Consumption:**
    - Consume `user-events` to build local user profiles (preferences, demographics).
    - Consume `raw-news` to index available news articles.
3.  **Recommendation Logic:** Implement content-based filtering (matching news categories/tags to user preferences).
4.  **Event Publishing:** Publish `recommendation-events` when a user interacts with a recommendation (e.g., click) or when new recommendations are generated.

## Impact Analysis
- **System:** Increases system complexity (new service).
- **Resources:** Requires additional compute and storage (likely a graph DB or vector DB).
- **User Experience:** Significantly improved relevance of news feed.

## Proposed Interfaces

### Kafka Interfaces

#### 1. Consuming (Consumer)
- **Topic:** `user-events`
    - **Schema:** `user-recommendation-profile.json`
    - **Action:** Update local user profile store.
- **Topic:** `raw-news`
    - **Schema:** `news-raw-event.json`
    - **Action:** Index news article for retrieval.

#### 2. Publishing (Producer)
- **Topic:** `recommendation-events`
- **Schema:** `recommendation-event.json` (See `docs/schemas/`)
- **Events:**
    - `RECOMMENDATION_GENERATED`: Fired when a list of articles is recommended to a user.
    - `RECOMMENDATION_CLICKED`: Fired when a user clicks a recommended link (feedback loop).

## Concrete Tasks
1.  **Initialize Service:** Create Spring Boot project with Kafka and DB dependencies.
2.  **Consume User Events:** Implement listener for `user-events` topic.
3.  **Consume News Events:** Implement listener for `raw-news` topic.
4.  **Implement Logic:** Create service to match user preferences against news attributes.
5.  **Publish Events:** Implement producer for `recommendation-events`.

## Configuration Management Considerations
- New service entry in `docker-compose.yml`.
- New database for recommendations.
- New Kafka topic `recommendation-events`.

## Approval Requirements
- Approval from (mindahun21).
