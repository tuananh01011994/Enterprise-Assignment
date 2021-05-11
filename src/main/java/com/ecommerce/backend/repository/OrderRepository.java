package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserAndProduct(long userId, long productId);
}
