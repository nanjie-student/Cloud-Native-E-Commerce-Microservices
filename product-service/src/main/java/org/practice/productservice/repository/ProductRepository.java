package org.practice.productservice.repository;

import org.practice.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.skuCode = :skuCode")
    boolean existsBySkuCode(@Param("skuCode") String skuCode);
}
