package org.practice.productservice.service;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @CircuitBreaker(name = "productService", fallbackMethod = "handleError")
    public String getProductInfo(String id) {
        // 模拟可能失败的逻辑
        return "Normal Product Info for " + id;
    }

    // 备选方案 (Fallback)：如果熔断了，执行这个方法
    public String handleError(String id, Exception e) {
        return "Fallback: Service is currently unavailable. Please try again later.";
    }
}

