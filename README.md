## Instant Cash Micro-service


1. **Overview**
    - This micro-service is designed to integrate with the Instant Cash Exchange House API, providing functionalities
      for managing transactions, including fetching outstanding transactions, checking transaction status, and
      confirming transaction statuses.
    - The service is built using Java and Spring Boot, and it utilizes the Spring Scheduler for periodic task execution.
    - It includes a properties file for configuration, a controller for handling API requests, and a service for
      processing the business logic.
    - The service is designed to be modular and easily extendable, allowing for future enhancements and additional
      features as needed.
    - The service is also designed to be robust and handle various error scenarios, ensuring that the system remains
      stable and reliable even in the face of unexpected issues.
    - The service includes comprehensive logging and monitoring capabilities, allowing for easy tracking of system
      performance and identifying potential issues.
    - The service is designed to be secure, with appropriate authentication and authorization mechanisms in place to
      protect sensitive data and ensure that only authorized users can access the system.
    - The service is designed to be scalable, allowing for easy expansion and growth as the needs of the business change
      over time.
    - Microservices are built using Spring Boot, which provides a lightweight framework for creating standalone
      applications.
    - Each microservice is designed to be independent and self-contained, with its own database and business logic.
    - Microservices communicate with each other using RESTful APIs or messaging queues (e.g., Kafka).
    - The microservices architecture allows for easy scaling and deployment of individual services, enabling faster
      development and deployment cycles.


      
3. **Components / services**
   - User Service
   - Eureka Service
   - Division Service
   - Department Service
   - API Gateway Service
   - Spring Cloud Config
   - Notification Service
   - Authentication Service
   - Instant Cash Exchange House API Service
   
   

3. **Technologies Used**
    - Java
    - Maven
    - Kafka
    - Redis
    - Oracle
    - Actuator
    - JWT Token
    - Spring Web
    - Spring AOP
    - Spring Boot
    - Spring Cloud
    - Spring Data JPA
    - Spring Scheduler
    - Spring Security
    - Log4j2 (for logging)
    - JUnit (for testing)
    - Jenkins (for CI/CD)
    - Postman (for API testing)
    - OPEN API (for API documentation)
    - Docker (for containerization)
    - Kubernetes (for orchestration)
    - Git (for version control)
    - Prometheus (for monitoring)
    - Grafana (for visualization)
    - H2 Database (for testing purposes)
    - SonarQube (for code quality analysis)
    - Lombok (for reducing boilerplate code)
    - MapStruct (for object mapping)
   

4. **Spring Cloud: for microservices architecture**
   - Spring Cloud Config: to manage external configurations
   - Spring Cloud Gateway: for API Gateway
   - Spring Cloud Sleuth: for distributed tracing
   - Spring Cloud Bus: for event-driven architecture
   - Spring Cloud Netflix Eureka: for service discovery
   - Spring Cloud OpenFeign: for declarative REST client
   - Spring Cloud Stream: for event-driven microservices
   - Spring Cloud Kubernetes: for Kubernetes integration
   - Spring Cloud Circuit Breaker: for circuit breaker pattern
   - Spring Cloud Load Balancer: for client-side load balancing
   - Spring Cloud Zipkin: for distributed tracing visualization
   - Spring Cloud Config Server: for external configuration management
   - Spring Cloud Consul: for service discovery and configuration management
   - Spring Cloud Resilience4j: for fault tolerance (Circuit Breaker, Retry, Rate Limiter, TimeLimiter)

5. **Maintain Reliability, Availability, Consistency of the microservices**

  - Circuit Breaker
  - Load Balancer
  - Service Discovery
  - Distributed Tracing
  - Event-Driven Architecture
  - Testing
  - Security
  - Logging
  - Error Handling
  - Configuration Management
  - Fault Tolerance
  - Scalability
  - Modularity
  - Extensibility
  - Robustness
  - Maintainability
  - Performance
  - Usability
  - Interoperability
  - Portability
  - Compatibility
  - Durability
  - Scalability
  - Flexibility