package org.practice.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    @LoadBalanced // 重点：开启负载均衡功能，WebClient 能够识别 Eureka 里的服务名
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
