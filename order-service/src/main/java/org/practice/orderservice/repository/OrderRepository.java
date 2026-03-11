package org.practice.orderservice.repository;


import org.practice.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OrderRepository extends JpaRepository<Order,Long> {
}
