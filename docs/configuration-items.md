# Configuration Items (CIs)

This document identifies all currently existing Configuration Items (CIs) within the News Recommendation Microservice repository.

## Services

| CI Name | Type | Location | Owning Service | Control Reason | Impact of Change |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **news-fetcher** | Service | `back/news-fetcher` | news-fetcher | Core data ingestion service. | Disruption in news gathering; downstream services starve. |
| **news** | Service | `back/news` | news | Core data persistence service. | Loss of news history; inability to serve news. |
| **user-service** | Service | `back/user-service` | user-service | Authentication & User management. | Users cannot login or register; security risks. |

## Integration Points (Kafka)

| CI Name | Type | Location | Owning Service | Control Reason | Impact of Change |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **raw-news** | Kafka Topic | `docker-compose.yml` (config), `application.yml` | news-fetcher | Primary data pipeline. | Breaking contract between fetcher and news service. |
| **user-events** | Kafka Topic | `docker-compose.yml` | user-service | User profile updates. | Downstream personalization fails. |
| **recommendation-events** | Kafka Topic | `docker-compose.yml` | recommendation-service | Recommendation feedback loop. | Analytics and personalization degradation. |
| **analytics-events** | Kafka Topic | `docker-compose.yml` | analytics-service | System insights. | Loss of business intelligence. |
| **RawNewsPayload** | Event Schema | `com.ds.newsfetcher.models.payload.RawNewsPayload` | news-fetcher | Defines data structure on the wire. | Serialization/Deserialization errors; data loss. |

## Data Persistence

| CI Name | Type | Location | Owning Service | Control Reason | Impact of Change |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **users table** | DB Schema | `V1__init_userdb.sql` | user-service | Defines user identity structure. | Database migration failures; application crash. |
| **news collection** | DB Schema | `com.ds.news.models.payloads.RawNewsPayload` | news | Defines news document structure. | Data inconsistency; query failures. |

## Configuration Files

| CI Name | Type | Location | Owning Service | Control Reason | Impact of Change |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **news-fetcher config** | Config | `back/news-fetcher/src/main/resources/application.yml` | news-fetcher | Controls connectivity & behavior. | Service connectivity failure (Kafka, Redis). |
| **news config** | Config | `back/news/src/main/resources/application.yml` | news | Controls connectivity & behavior. | Service connectivity failure (Kafka, Mongo). |
| **user-service config** | Config | `back/user-service/src/main/resources/application.yml` | user-service | Controls connectivity & behavior. | Service connectivity failure (DB, JWT). |
| **docker-compose** | Infrastructure | `docker-compose.yml` | System | Defines runtime environment. | Environment startup failure; networking issues. |

## Planned Change Requests

| CR ID | Title | Status | Affected Services |
| :--- | :--- | :--- | :--- |
| **CR-01** | User Service Profile Events Support | DRAFT | user-service |
| **CR-02** | Create Recommendation Service | DRAFT | recommendation-service |
| **CR-03** | Create Analytics Service | DRAFT | analytics-service |
