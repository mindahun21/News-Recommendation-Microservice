# Change Request: User Service Profile Interest Support

| Field | Value |
| :--- | :--- |
| **CR ID** | CR-01 |
| **Title** | User Service Profile Interest Support |
| **Requested By** | mindahun21 |
| **Assigned To** | natidev |
| **Date** | 2025-12-23 |
| **Status** | DRAFT |

## Affected Configuration Items
- **Service:** `user-service`
- **Interface:** REST API (New: `/users/interested`)

## Current Baseline
- The `user-service` currently manages user authentication and basic profile data.
- It does not currently provide a mechanism to identify users interested in specific news content.

## Proposed Change Summary
Extend the `user-service` to provide an interface that accepts a news item and returns a list of users interested in that specific news item.

## Motivation
To enable personalized news recommendations, the recommendation service needs to know which users are interested in a specific piece of news.

## Scope
1.  **Interface:** Implement an interface (likely a REST endpoint) that accepts a news item and returns interested users.

## Impact Analysis
- **user-service:** Requires implementation of logic to match users to news content based on preferences.
- **Downstream:** Enables `recommendation-service` to target specific users.

## Proposed Interfaces

### Interest Check Interface

#### 1. Find Interested Users
- **Endpoint:** `POST /users/interested` (or similar internal method)
- **Description:** Accepts a single news item and returns users interested in it.
- **Request:** JSON representation of a single news item.
- **Response:** List of user profiles interested in the news.

## Approval Requirements
- Review by `user-service` lead (natidev).

