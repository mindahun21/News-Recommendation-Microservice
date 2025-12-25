# Change Request: News Service Analytics Controller

| Field | Value                                          |
| :--- |:-----------------------------------------------|
| CR ID | CR-07                                          |
| Title | Implement Analytics Controller in News Service |
| Requested By | natidev                                        |
| Assigned To | mindahun21                                     |
| Date | 2025-12-25                                     |
| Status | DRAFT                                          |

## Affected Configuration Items
- Service: news-service
- File: AnalyticsController.java

## Current Baseline
- Analytics functionality was originally planned for a separate analytics-service (CR-03).
- The news-service focused solely on serving news content.

## Proposed Change Summary
Add an AnalyticsController to the news-service.

## Motivation
To support analytics-related operations directly within the news-service context, possibly to simplify the architecture or provide immediate access to news-related metrics without a separate service overhead initially.

## Scope
1.  Controller Creation: Create AnalyticsController in com.ds.news.controllers.
2.  Endpoint Definition: Define base path /analytics.
3.  Implementation: (Currently a placeholder/skeleton for future expansion).

## Impact Analysis
- System: Shifts analytics responsibility (partially) to news-service.
- Architecture: Deviates from the microservices split defined in CR-03.
- Maintainability: May couple analytics logic with news serving logic.

## Proposed Interfaces
### REST API
- Base Path: /analytics
- *(Specific endpoints to be defined in future iterations)*

## Concrete Tasks
1.  Create AnalyticsController.java.
2.  Annotate with @RestController and @RequestMapping("/analytics").

## Approval Requirements
- Approval from Architecture Lead.