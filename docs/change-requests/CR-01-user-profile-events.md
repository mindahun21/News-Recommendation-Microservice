# Change Request: User Service Profile Events Support

| Field | Value |
| :--- | :--- |
| **CR ID** | CR-01 |
| **Title** | User Service Profile Events Support |
| **Requested By** | mindahun21 |
| **Assigned To** | natidev |
| **Date** | 2025-12-19 |
| **Status** | DRAFT |

## Affected Configuration Items
- **Service:** `user-service`
- **Schema:** `UserSchema` (PostgreSQL)
- **Interface:** Kafka Topic (New: `user-events`)
- **Interface:** REST API (New: `/users/{userId}/profile`)

## Current Baseline
- The `user-service` currently manages user authentication and basic profile data (username, password, roles) stored in PostgreSQL.
- It does not currently publish events or integrate with recommendation/analytics services.

## Proposed Change Summary
Extend the `user-service` to maintain a richer user profile model suitable for recommendation and analytics, publish user lifecycle events, and expose REST endpoints for profile management.

## Motivation
To enable personalized news recommendations and user behavior analytics, downstream services need access to user profile data and real-time updates. Polling the user database is inefficient and creates tight coupling. Event-driven architecture is preferred.

## Scope
1.  **Data Model:** Extend `User` entity to include fields relevant for recommendations (preferences, demographics).
2.  **REST API:** Implement endpoints for retrieving and updating user profiles.
3.  **Event Publishing:** Implement a Kafka producer to publish `UserCreatedEvent` and `UserUpdatedEvent`.
4.  **Event Consumption:** Consume `recommendation-events` (e.g., `RECOMMENDATION_CLICKED`) and `analytics-events` (e.g., `USER_ENGAGEMENT`) to update user preferences dynamically.

## Impact Analysis
- **user-service:** Moderate impact. Requires schema migration, new Kafka producer/consumer, and API endpoints.
- **Downstream:** Enables `recommendation-service` and `analytics-service`.
- **Performance:** Minimal impact on login latency; slight overhead for profile updates due to event publishing.

## Proposed Interfaces

### REST API

#### 1. Get User Profile
- **Endpoint:** `GET /users/{userId}/profile`
- **Description:** Retrieves the full user profile including preferences.
- **Response:** JSON matching `user-recommendation-profile.json`.

#### 2. Update User Profile
- **Endpoint:** `POST /users/{userId}/profile`
- **Description:** Updates user preferences and demographics.
- **Request:** JSON matching `user-recommendation-profile.json` (partial updates allowed).
- **Response:** 200 OK with updated profile.

### Kafka Interfaces

#### 1. Publishing (Producer)
- **Topic:** `user-events`
- **Events:**
    - `USER_CREATED`: Fired when a new user registers.
    - `USER_UPDATED`: Fired when profile is updated via REST or internal logic.
- **Schema:** `user-recommendation-profile.json` wrapped in an event envelope.

#### 2. Consuming (Consumer)
- **Topic:** `recommendation-events`
    - **Event Type:** `RECOMMENDATION_CLICKED`
    - **Action:** Update user preferences (e.g., boost category weight).
- **Topic:** `analytics-events`
    - **Event Type:** `USER_ENGAGEMENT` (e.g., long read time)
    - **Action:** Update user engagement metrics in profile.

## Configuration Management Considerations
- New Kafka topic `user-events` must be created.
- New schema `user-recommendation-profile.json` must be versioned.

## Approval Requirements
- Review by `user-service` lead (natidev).
- Approval from  (natidev).
