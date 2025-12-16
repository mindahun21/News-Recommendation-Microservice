# News Recommendation Microservice Platform


## Project Overview

This is a distributed microservice platform for personalized news delivery, consisting of independent services that communicate via events and APIs. The system provides personalized news recommendations, user management, content ingestion, analytics, and notifications.

## Repository Structure

This repository uses a **dual-branch structure**:

- **`front` branch**: Contains the frontend application (web/mobile interface)
	
- **`back` branch**: Contains all backend microservices and infrastructure

##  Backend Architecture

The backend follows a microservices architecture with event-driven communication. Each service is independently deployable and scalable.

### Service Overview

#### 1. **User Service**

**Purpose**: Centralized user management and profile tracking

- **Responsibilities**:
    
    - User CRUD operations (Create, Read, Update, Delete)
        
    - Management of user interests and news category preferences
        
    - Tracking user reading behavior (articles read, clicks, ratings)
        
    - Providing user data APIs for other services
        
    - Publishing user-related events
#### 2. **News Service**

**Purpose**: News content management and ingestion

- **Responsibilities**:
    
    - Article CRUD operations
        
    - Storage of article metadata (title, category, author, source, date)
        
    - Ingestion from external sources (APIs, RSS feeds)
        
    - Providing news data to other services and frontend
        
    - Publishing content update events

#### 3. **Recommendation Service**

**Purpose**: Personalized news recommendation engine

- **Responsibilities**:
    
    - Consuming events from User and News services
        
    - Maintaining user-news interaction data
        
    - Generating personalized news lists based on user behavior
        
    - Providing recommendation APIs to frontend
        
    - Publishing recommendation events
#### 4. **API Gateway**

**Purpose**: Unified entry point and traffic management

- **Responsibilities**:
    
    - Routing client requests to appropriate services
        
    - Authentication and authorization
        
    - Rate limiting and request validation
        
    - Service discovery integration
        
    - Providing uniform API interface
        

#### 5. **Analytics Service**

**Purpose**: Usage tracking and system insights

- **Responsibilities**:
    
    - Aggregating user interactions (reads, clicks, likes, time spent)
        
    - Generating usage reports and performance metrics
        
    - Providing data to improve recommendation accuracy
        
    - Dashboard APIs for administrative reporting
        
    - Historical data storage for trend analysis
        

#### 6. **Notification Service**

**Purpose**: User communication and alerts

- **Responsibilities**:
    
    - Sending push notifications for breaking news
        
    - Email and in-app alerts for personalized recommendations
        
    - Notification scheduling and delivery management
        
    - User notification preferences management
        
## 🔧 Technology Stack

### Backend Services

- **Language**:  Python, Java
    
- **Framework**:  FastAPI, Spring Boot
	
- **Communication**: REST APIs, Message Broker (Kafka/RabbitMQ)
    
- **Database**: Service-specific databases (PostgreSQL, MongoDB, Redis)
    
- **Containerization**: Docker, Kubernetes for orchestration
    
%% - **Monitoring**: Prometheus, Grafana, ELK Stack %%
    

### Frontend

- **Framework**: Next.js
    
- **State Management**: Zustand/Redux
    
- **Build Tools**: Webpack, Babel
    
- **Styling**: Component Library
    

## Getting Started

### Prerequisites

- Docker and Docker Compose
    
- Python 3.x / Java 17
    
- Message Broker (Kafka/RabbitMQ)
	
- Gradle/Maven
	
- Node 20 or later

### Backend Setup (Back Branch)

```sh 
git checkout back
```

## 📁 Project Structure (Back Branch)

```text

back/
├── user-service/
├── news-service/
├── recommendation-service/
├── api-gateway/
├── analytics-service/
├── notification-service/
├── shared/
├── docker-compose.yml
├── .env.example
└── README.md

```
