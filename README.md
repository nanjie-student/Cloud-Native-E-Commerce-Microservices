# Cloud-Native E-Commerce Microservices
    Transitioning from Distributed Systems to Cloud-Managed Infrastructure

    🚀 Evolution: From Spring Cloud to AWS Cloud-Native
    Previously, this project focused on the Spring Cloud Ecosystem for local service orchestration. I have since 
    "leveled up" the architecture to be Production-Ready by containerizing the services and deploying them to AWS.

    🏗️ Architectural Upgrades
    1. Containerization & Registry (Docker + AWS ECR)
    Previous: Manual JAR execution on local JVMs.

    Upgrade: Implemented a Docker-first workflow. Created optimized, multi-stage Dockerfiles using 
    eclipse-temurin:17-jre to ensure Environment Parity.
    Result: Securely versioned and stored microservice images in AWS ECR (Elastic Container Registry).

    2. Service Discovery & Orchestration
    Previous: Manual Eureka Server management.
    Upgrade: Moving toward AWS ECS (Elastic Container Service). By using AWS native networking, we reduce the overhead of maintaining dedicated discovery servers, allowing AWS to handle health checks and service connectivity.

    3. Resilience & Fault Tolerance
    Standard: Retained Resilience4j for Circuit Breaking and Fallback mechanisms.

    Cloud-Scale: Combined application-level resilience with AWS CloudWatch monitoring 
    to ensure high availability and proactive failure detection.

    4. Database & Infrastructure
    Local: PostgreSQL running in Docker containers.

    Future-Ready: Designed for AWS RDS integration, separating the stateful data layer from the stateless compute layer 
    to achieve true scalability.


    




    
    基于 Spring Cloud 构建了微服务电商平台，采用 Eureka 实现动态服务治理，利用 Spring Cloud Gateway 作为统一流量入口。集成 
    Resilience4j 实现了熔断与降级机制，有效提升了系统的可用性（Availability）；并配合 Config Server 实现了分布式环境下配置的集中
    化管理与环境隔离




    项目进展回顾 (2026-03-12)
    
    今日完成了从“单体部署思维”向“云原生架构”的关键转型，核心解决了微服务的配置管理与网络通信问题。
    
    ### 1. 基础设施层 (Infrastructure)
    - **数据库隔离**：实现了 Product 和 Order 服务的数据库完全解耦。
        - `postgres-product`: 运行于容器 5432，映射宿主机 5432。
        - `postgres-order`: 运行于容器 5432，映射宿主机 5433（避免端口冲突）。
    
    ### 2. 配置管理层 (Configuration)
    - **引入 Config Server**：搭建了中心化配置服务器。
      - **外部化配置**：通过 Docker Volumes 将宿主机 `./config-server/src/main/resources/shared-configs` 挂载至容器内部，实现配置与镜像解耦。
      - **原生文件支持**：启用 `spring.profiles.active=native`，支持直接读取本地文件系统中的 YAML 配置。
    
    ### 3. 业务服务集成 (Service Integration)
    - **Bootstrap 激活**：在业务服务中引入 `spring-cloud-starter-bootstrap` 和 `spring-cloud-starter-config`，确保服务启动时优先从 Config Server 加载配置。
      - **服务发现**：所有服务（Product, Order, Config）均已成功注册至 Eureka Discovery Server。
    
    ### 4. 网络通信 (Networking)
    - **端口映射**：修正了 `docker-compose.yml` 中的端口暴露配置，允许外部（Host）直接通过 REST API 访问容器化后的服务。


讲项目是干嘛的，每天的成果是什么？
    We have successfully containerized our microservices and deployed them into a Cloud-Native environment using Docker 
    Compose. The core of our system relies on Distributed Configuration Management; each service dynamically pulls its 
    settings from a central Config Server at runtime. This approach, combined with Service Discovery via Eureka, ensures 
    our architecture is scalable, decoupled, and ready for a production-grade CI/CD pipeline.