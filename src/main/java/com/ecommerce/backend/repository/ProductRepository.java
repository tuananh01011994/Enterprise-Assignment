package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductNameContaining(String productName);
    List<Product> findByProductDescriptionContaining(String detail);
    List<Product> findByStore_Id(long id);

    Optional<Product> findById(long productId);
}
