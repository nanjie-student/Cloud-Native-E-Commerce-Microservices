package org.practice.productservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.practice.productservice.entity.Product;
import org.practice.productservice.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product product) {
        productRepository.save(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode) {
        // 简单逻辑：这里你可以去数据库查，现在我们先写死只要有 code 就返回 true
        return productRepository.existsBySkuCode(skuCode);
    }

    @GetMapping("/test-circuit")
    @CircuitBreaker(name = "productService", fallbackMethod = "myFallback")
    public String testCircuit(@RequestParam int num) {
        if (num < 0) {
            throw new RuntimeException("故意报错！");
        }
        return "服务正常，数字是：" + num;
    }

    // 降级方法：参数和返回值必须和原方法一致，最后加个 Exception
    public String myFallback(int num, Exception e) {
        return "【熔断开启】由于输入是 " + num + "，系统已自动保护。错误原因：" + e.getMessage();
    }
}
