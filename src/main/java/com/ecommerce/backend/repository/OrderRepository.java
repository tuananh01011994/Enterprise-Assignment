package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUserAndProduct(User user, Product product);
    boolean existsByUserAndProduct(User user, Product product);
    List<Order> findByUser(User user);
    Optional<Order> findById(long id);
    List<Order> findAll();
}
