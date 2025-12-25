# Configuration Item (CI) Register

**Project:** News Recommendation Microservice Platform
**Date:** 2025-12-25

## 1. Source Code CIs (Microservices)

| CI Name | Version | Owner | Category | Status | Location |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **user-service** | 1.0.0 | Backend Team | Service | Active | `back/user-service` |
| **news-service** | 1.0.0 | Backend Team | Service | Active | `back/news` |
| **news-fetcher** | 1.0.0 | Backend Team | Service | Active | `back/news-fetcher` |
| **recommendation-service** | 1.1.0 | AI Team | Service | Active | `back/recommendation-service` |
| **ai-service** | 1.0.0 | AI Team | Service | Active | `back/ai-service` |
| **api-gateway** | 1.0.0 | DevOps Team | Service | Active | `back/gateway` |
| **discovery-service** | 1.0.0 | DevOps Team | Service | Active | `back/discovery` |
| **analytics-service** | 0.1.0 | Backend Team | Service | Planned | `back/analytics-service` |
| **notification-service** | 0.1.0 | Backend Team | Service | Planned | `back/notification-service` |

## 2. Infrastructure CIs

| CI Name | Version | Owner | Category | Status | Location |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **docker-compose.yml** | 1.0 | DevOps Team | Infrastructure | Active | `docker-compose.yml` |
| **Dockerfile (User)** | 1.0 | Backend Team | Infrastructure | Active | `back/user-service/Dockerfile` |
| **Dockerfile (News)** | 1.0 | Backend Team | Infrastructure | Active | `back/news/Dockerfile` |
| **Dockerfile (Gateway)** | 1.0 | DevOps Team | Infrastructure | Active | `back/gateway/Dockerfile` |
| **Dockerfile (Discovery)** | 1.0 | DevOps Team | Infrastructure | Active | `back/discovery/Dockerfile` |

## 3. Documentation CIs

| CI Name | Version | Owner | Category | Status | Location |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **SCM Plan** | 1.0 | SCM Manager | Document | Active | `docs/scmp.md` |
| **Release v1.0 Notes** | 1.0 | Release Manager | Document | Released | `docker/Release_v1.0_Notes.md` |
| **Release v1.1 Notes** | 1.1 | Release Manager | Document | Released | `docs/Release_v1.1_Notes.md` |
| **Project README** | 1.0 | Lead Developer | Document | Active | `README.md` |
| **Configuration Items** | 1.0 | SCM Manager | Document | Active | `docs/configuration-items.md` |

## 4. Data & Schema CIs

| CI Name | Version | Owner | Category | Status | Location |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **User DB Schema** | 1.0 | Backend Team | Database | Active | `back/user-service/src/main/resources/db/migration` |
| **News API Schema** | 1.0 | Backend Team | API | Active | `back/news/src/main/resources/api` |
