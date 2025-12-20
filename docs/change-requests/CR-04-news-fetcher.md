# Change Request: News Fetcher JDK Downgrade

| Field | Value                            |
| :--- |:---------------------------------|
| **CR ID** | CR-04                            |
| **Title** | Downgrade News Fetcher JDK to 17 |
| **Requested By** | natidev                          |
| **Assigned To** | mindahun21,natidev               |
| **Date** | 2025-12-19                       |
| **Status** | COMPLETED                        |

## Affected Configuration Items
- **Service:** `news-fetcher`
- **File:** `pom.xml`
- **File:** `docker-compose.yml` (if build args differ)

## Current Baseline
- The `news-fetcher` service is currently configured to run on JDK 21.

## Proposed Change Summary
Downgrade the Java Development Kit (JDK) version for the `news-fetcher` service from version 21 to version 17 (LTS).

## Motivation
The current utilization of JDK 21 has led to stability issues and compatibility conflicts within the `news-fetcher` environment. Downgrading to JDK 17, a proven Long-Term Support release, resolves these runtime issues and ensures consistent behavior with the current dependency stack.

## Scope
1.  **Configuration Update:** Modify `pom.xml` properties to target Java 17.
2.  **Environment Update:** Ensure the Docker/Build environment uses a JDK 17 base image.
3.  **Verification:** Rebuild the project and run existing test suites to ensure no language-feature regressions.

## Impact Analysis
- **System:** Improved stability and reduced risk of runtime crashes related to newer JVM features.
- **Resources:** Negligible impact on build time or memory usage.
- **Business:** Ensures continuous and reliable ingestion of news data without interruption.

## Proposed Interfaces
*No changes to external interfaces or Kafka topics.*

## Concrete Tasks
1.  **Update POM:** Change `<java.version>` in `back/news-fetcher/pom.xml` from `21` to `17`.
2.  **Verify Dependencies:** Check Spring Boot and other library compatibility with JDK 17 (likely compatible as 17 is standard).
3.  **Local Test:** Run `mvn clean install` using JDK 17 locally to verify compilation.
4.  **Runtime Test:** Start the service and verify the scheduler triggers correctly.

## Configuration Management Considerations
- `pom.xml` version property update.
- Verify CI/CD pipeline configuration (if applicable) aligns with JDK 17.

## Approval Requirements
- Approval from (mindahun21).