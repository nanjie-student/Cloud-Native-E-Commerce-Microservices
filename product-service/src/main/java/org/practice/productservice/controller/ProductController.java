package org.practice.productservice.controller;

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
}
