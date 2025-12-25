# Change Request: Create AI Enrichment Service

| Field | Value                            |
| :--- |:---------------------------------|
| CR ID | CR-05                            |
| Title | Create AI Enrichment Service     |
| Requested By | natidev                          |
| Assigned To | mindahun21                       |
| Date | 2025-12-25                       |
| Status | DRAFT                            |

## Affected Configuration Items
- Service: ai-service (New Service)
- File: docker-compose.yml
- File: settings.gradle (if included in build, though likely Python standalone)

## Current Baseline
- The system currently lacks a dedicated service for natural language processing tasks such as text embedding generation, language detection, and summarization.
- Recommendation logic relies on simple keyword matching or lacks semantic understanding.

## Proposed Change Summary
Create a new microservice named ai-service using Python and FastAPI to provide AI capabilities to the platform.

## Motivation
To enhance the recommendation quality, we need semantic vectors (embeddings) for news articles. Additionally, language detection is required to filter content for users. A dedicated Python-based service allows leveraging the rich ecosystem of ML libraries (HuggingFace, etc.) which is more suitable than Java for these tasks.

## Scope
1.  Service Creation: Initialize a new Python project ai-service.
2.  API Implementation: Create a REST endpoint /enrich that accepts text and returns:
    -   Embedding: Vector representation using sentence-transformers/all-MiniLM-L6-v2.
    -   Language: Detected language code (e.g., 'en', 'es').
    -   Summary: (Optional/Planned) A brief summary of the text.
3.  Dockerization: Create a Dockerfile for the service.

## Impact Analysis
- System: Adds a new dependency for services that need enrichment (e.g., news-service, recommendation-service).
- Resources: Requires CPU/Memory for running the ML model. all-MiniLM-L6-v2 is lightweight but still adds overhead.
- Business: Enables "More like this" features and better personalization.

## Proposed Interfaces
### REST API
- POST /enrich
    -   Request: {"text": "string"}
    -   Response:
        {
        "embedding": [0.1, ...],
        "language": "en",
        "summary": "..."  // Optional
        }


## Concrete Tasks
1.  Create directory back/ai-service.
2.  Setup requirements.txt with fastapi, uvicorn, sentence-transformers, langdetect.
3.  Implement main.py with the FastAPI app and model loading.
4.  Add Dockerfile.
5.  Register service in docker-compose.yml.

## Configuration Management Considerations
- Ensure the model is downloaded during build or cached to avoid runtime download delays.
- Python version should be 3.9+.

## Approval Requirements
- Approval from Architecture Lead.