# Cloud-Native-E-Commerce-Microservices
    A high-performance, scalable e-commerce platform built with Spring Cloud and deployed on AWS/Azure. This project 
    demonstrates modern distributed system patterns including Service Discovery, Circuit Breaking, and Containerization.


    I built a Microservices Architecture using the Spring Cloud ecosystem. This project consists of multiple independent 
    services that communicate with each other seamlessly.
    
        1.Service Discovery: "I used Eureka for service registration and discovery, allowing services to find each other dynamically."

        2.Load Balancing: "I implemented client-side load balancing using Spring Cloud LoadBalancer with WebClient to distribute 
        traffic efficiently."

        3.API Gateway: "I designed a Unified Gateway as the single entry point for all requests, handling routing and
        cross-cutting concerns."

        4.Centralized Config: "I integrated Spring Cloud Config to manage configurations across all environments from 
        a central repository."

        5.Fault Tolerance: "To ensure system resilience, I implemented the Circuit Breaker pattern using Resilience4j,    
        which prevents cascading failures through graceful degradation (fallback methods)."

    基于 Spring Cloud 构建了微服务电商平台，采用 Eureka 实现动态服务治理，利用 Spring Cloud Gateway 作为统一流量入口。集成 
    Resilience4j 实现了熔断与降级机制，有效提升了系统的可用性（Availability）；并配合 Config Server 实现了分布式环境下配置的集中
    化管理与环境隔离