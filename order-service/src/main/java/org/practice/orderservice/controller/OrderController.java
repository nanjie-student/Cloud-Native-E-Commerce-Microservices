package org.practice.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.practice.orderservice.entity.Order;
import org.practice.orderservice.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;


@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {

    private final WebClient.Builder webClientBuilder;

    private final WebClient webClient;

    private final OrderRepository orderRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        // 1. 调用 Product Service 检查商品
        // 假设 Product Service 有个接口：GET http://localhost:8081/api/product/check?skuCode=xxx
//        Boolean result = webClient.get()
//                .uri("http://localhost:8081/api/product/check",
//                        uriBuilder -> uriBuilder.queryParam("skuCode", order.getSkuCode()).build())
//                .retrieve()
//                .bodyToMono(Boolean.class) // 将响应转为 Boolean
//                .block(); // 阻塞等待结果（微服务初期最简单的写法）

        Boolean result = webClientBuilder.build().get()
                .uri("http://product-service/api/product/check", // 不再用 localhost:8081
                        uriBuilder -> uriBuilder.queryParam("skuCode", order.getSkuCode()).build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        // 2. 根据结果判断
        if (Boolean.TRUE.equals(result)) {
            order.setOrderNumber(UUID.randomUUID().toString());
            orderRepository.save(order);
            // 只有成功才返回 201
            return ResponseEntity.status(HttpStatus.CREATED).body("Order Placed Successfully");
        } else {
            // 失败返回 400 或 409
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product is not in stock");
        }
    }
}
