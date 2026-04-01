# 云原生电商微服务平台 (Cloud-Native E-Commerce)
    本项目是一个基于 Spring Boot 3.x 构建的响应式微服务架构系统。项目核心价值在于实现了从传统分布式架构（Spring Cloud）向现代云原生架构（AWS + Docker）的全面演进。

    🌟 架构演进：从分布式到云原生
    本项目不仅实现了业务逻辑，更完成了生产级的架构升级：
    容器化标准 (Containerization)：
    现状：编写了多阶段构建的 Dockerfile，基于 eclipse-temurin:17-jre 打造了仅 160MB 左右的轻量级生产镜像。
    价值：消除了“在我电脑上能跑”的环境差异，实现了环境一致性（Environment Parity）。

    云端资产管理 (AWS ECR)：
    现状：打通了本地开发环境与 AWS ECR (Elastic Container Registry) 的链路。
    价值：实现了镜像的版本化管理和云端安全分发，为 CI/CD 自动化部署奠定了基础。

    服务治理与可靠性：
    现状：保留了 Resilience4j 熔断机制，并正向 AWS ECS/Fargate 托管集群迁移。
    价值：利用云原生服务替代了手动维护的 Eureka，降低了运维成本，提升了系统的弹性扩展（Scalability）能力。

    🛠️ 技术栈
    后端核心：Java 17, Spring Boot 3, Spring Data JPA
    微服务治理：Spring Cloud (Config, Gateway), Resilience4j
    容器化/云原生：Docker, AWS ECR, AWS ECS
    数据库：PostgreSQL (本地容器化 / 云端 RDS)




开发遇到的问题：
1. 基础设施缺失：CloudWatch 日志组 (ResourceNotFound)
   现象：任务启动后立刻停止，报错 The specified log group does not exist。
技术原委：Fargate 是“无服务器”容器，它没有本地硬盘存日志。我们在“任务定义”里指定了 awslogs 驱动，要求它把控制台输出（System.out）实时传给 CloudWatch。
复盘：如果云端没有预先创建那个名为 /ecs/product-service-task 的“容器”，Fargate 就会因为无法初始化日志环境而崩溃。
解决方案：手动在正确的 Region (us-east-2) 下预创建同名日志组。

2. 网络隔离：安全组 (Security Group) 防火墙
   现象：任务显示 RUNNING 状态，公网 IP 也有了，但浏览器访问时报 ERR_CONNECTION_TIMED_OUT。
技术原委：AWS 遵循“默认拒绝”（Default Deny）原则。即便你的 Spring Boot 监听了 8080 端口，但外网流量在进入 VPC（虚拟私有云）时被安全组挡住了。
复盘：必须显式地配置“入站规则”（Inbound Rules）。

解决方案：添加自定义 TCP 规则，允许 0.0.0.0/0 访问 8080 端口。

3. 环境一致性：IAM 角色与权限 (Permission Gap)
   现象：最初无法拉取镜像，或者无法自动创建日志流。
技术原委：ECS 任务需要一个“身份”（Role）去代表它调用其他 AWS 服务。
复盘：需要 ecsTaskExecutionRole 这个角色，并且它必须拥有 AmazonECSTaskExecutionRolePolicy 权限，才能去 ECR 拿镜像、去 CloudWatch 写日志。
解决方案：手动创建并关联 IAM 角色。

我们最终的目标（理想态）
一旦这三个坑填平，你的服务就会像下图这样：

用户浏览器 -> 通过互联网 -> 穿过 Security Group (8080门)。

进入 Fargate 容器 -> 运行你的 Spring Boot JAR。

Spring Boot -> 产生的日志流向 CloudWatch。

你 -> 可以在任何地方看到数据，并在 CloudWatch 监控运行状态。
