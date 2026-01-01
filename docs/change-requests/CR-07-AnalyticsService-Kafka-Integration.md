# Change Request: Analytics Service Kafka Integration

| Field | Value |
| :--- | :--- |
| **CR ID** | CR-07 |
| **Title** | Analytics Service Kafka Integration |
| **Requested By** | mindahun21 |
| **Assigned To** | nati-des |
| **Date** | 2026-01-01 |
| **Status** | DRAFT |

## Affected Configuration Items
- **Service:** `analytics-service`
- **Components:** `AnalyticsController`, `AnalyticsProducerService`
- **Kafka Topics:** `analytics.category`, `analytics.source`

## Current Baseline
- The `analytics-service` needs to provide an interface for external systems (like a frontend or other microservices) to report user activity.
- These events need to be published to Kafka topics so that the `user-service` (and potentially other services) can consume them to update user profiles and embeddings.

## Proposed Change Summary
Implement a production-ready Kafka producer integration in the `analytics-service`. This includes creating official REST endpoints for publishing user interest events (category and source) to Kafka topics.

## Motivation
To centralize analytics event collection in the `analytics-service` and ensure these events are reliably streamed to Kafka for downstream processing, such as profile updates in the `user-service`.

## Scope
1.  **New Controller**: Create an `AnalyticsController` in the `analytics-service` with the following endpoints:
    - `POST /analytics/category`: Publishes a category interest event.
    - `POST /analytics/source`: Publishes a source interest event.
2.  **Service Layer**: Implement an `AnalyticsProducerService` using reactive Kafka (`KafkaSender`) to handle event publishing.
3.  **Kafka Topics**:
    - `analytics.category`: For category-related events.
    - `analytics.source`: For source-related events.
4.  **Payload Structure**: Use the `AnalyticsEvent` DTO:
    ```json
    {
      "userId": "string",
      "value": "string",
      "action": "ADD|REMOVE|UPDATE"
    }
    ```

## Impact Analysis
- **analytics-service**: Adds new REST endpoints and Kafka producer logic.
- **user-service**: Will consume these topics to update user profiles and trigger embedding recalculations.
- **Kafka**: Requires the existence of `analytics.category` and `analytics.source` topics.

## Proposed Interfaces

### Analytics Interface

#### 1. Publish Category Event
- **Endpoint:** `POST /analytics/category`
- **Description:** Publishes a category interest event to the `analytics.category` topic.
- **Request Body:** `AnalyticsEvent`
- **Response:** `202 Accepted`

#### 2. Publish Source Event
- **Endpoint:** `POST /analytics/source`
- **Description:** Publishes a source interest event to the `analytics.source` topic.
- **Request Body:** `AnalyticsEvent`
- **Response:** `202 Accepted`

## Approval Requirements
- Review by `analytics-service` lead.
